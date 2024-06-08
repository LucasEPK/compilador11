package SemanticAnalyzer.AST;


import CodeGeneration.CodeGenerator;
import Exceptions.SemanticExceptions.AST.InvalidNilPrimitive;
import Exceptions.SemanticExceptions.AST.TypesDontMatch;
import Exceptions.SemanticExceptions.AST.VoidAsignation;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Variable;

import java.util.Map;

/**
 * Clase representate la asignación en nustro AST
 * @author Yeumen Silva
 */

public class AsignationNode extends SentenceNode implements Commons {

    ExpressionNode left;
    ExpressionNode right;


    public AsignationNode(String struct, String method) {
        super(struct, method);
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     * @autor Yeumen Silva
     */

    @Override
    public String toJson(int tabs) {

            String json = addtabs(tabs) + "{\n";
            json += addtabs(tabs+1) + "\"nombre\": \"" + "Asignation" + "\",\n";
            json += addtabs(tabs+1) + "\"left\": " + left.toJson(tabs+1) + ",\n";
            json += addtabs(tabs+1) + "\"right\": " + right.toJson(tabs+1) + "\n";
            json += addtabs(tabs) + "}\n";
            return json;

    }

    @Override
    public void consolidate(AST ast ) {
        if(this.left.getConsolidated() == false){
            this.left.consolidate(ast);
        }
        if(this.right.getConsolidated() == false){
            this.right.consolidate(ast);
        }


        //Retornar void no permite asignaciones
        if(this.right.getType().equals("void")){
            throw new VoidAsignation(this.right.getToken());
        }


        //Si el lado izquierdo es un array, la asignación también lo es
        if(this.left.getIsArray()){
            this.setIsArray(true);
        }



        if(this.left.getType().equals(this.right.getType()) == false){
            //Verifico si es una subclase
            if(ast.isSubStruct(this.left.getType(),this.right.getType()) == false){
                //Si es una referencia a un objeto puede ser nil
                if(this.right.getType().equals("nil") ){
                    if(ast.isPrimitive(this.left.getType()) && !this.left.getIsArray()){
                        throw new InvalidNilPrimitive(this.left.getToken());
                    }
                }else {
                    throw new TypesDontMatch(this.left.getToken());
                }
            }
        }


        setConsolidated(true);

    }


    /**
     * Método que agrega una cantidad de tabulaciones a un string
     * @param tabs cantidad de tabulaciones a agregar
     * @return string con las tabulaciones agregadas
     * @autor Yeumen Silva
     */
    @Override
    public String addtabs(int tabs) {
        String tabsString = "";
        for (int i = 0; i < tabs; i++) {
            tabsString += "\t";
        }
        return tabsString;
    }

    @Override
    public String generateCode(CodeGenerator codeGenerator) {
        String textCode = "\t# Asignacion de variable\n";
        // TODO: chequear con tabla de simbolos en que lugar del stack está
        SymbolTable symbolTable = codeGenerator.getSymbolTable();
        // TODO: si es un new, hacer CIR
        textCode += left.generateCode(codeGenerator);
        int currentVariablePos = 0; // Con esto se obtiene la posicion de la variable en la lista de variables declaradas
        if(!((IdNode)left).isChained()) { // Si no tiene encadenado:
            String leftName = left.getToken().getLexeme();

            // Buscamos la posición de la variable en la lista de variables declaradas
            Map<String,Variable> variableList = symbolTable.getStructMethodDeclaredVariables(this.getStruct(), this.getMethod());
            // Recorro la lista de todas las variables
            for (String varName : variableList.keySet()){
                if (varName.equals(leftName)){
                    break;
                }
                currentVariablePos += 1;
            }
        }

        textCode += right.generateCode(codeGenerator);
        if(right instanceof LiteralNode){ // Si estamos asignando un literal
            // Meto el valor asignado de la variable en la posicion correcta del stack
            if( !((LiteralNode)right).getType().equals("Str")){ // Esto se debe a que Str tiene metodos, osea que necesita de un CIR
                int variableStackPos = -4 * (currentVariablePos+1);
                textCode += "\tsw $v0, "+variableStackPos+"($fp)\t# Meto el valor asignado de la variable en el lugar de la variable del stack\n";
            }
        }
        textCode += "\t# FIN asignacion de variable\n";
        return textCode;
    }
}
