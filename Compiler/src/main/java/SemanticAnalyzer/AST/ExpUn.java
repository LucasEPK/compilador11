package SemanticAnalyzer.AST;


import CodeGeneration.CodeGenerator;
import Exceptions.SemanticExceptions.AST.InvalidLogicalComparation;
import Exceptions.SemanticExceptions.AST.NeedToBeInt;
import Exceptions.SemanticExceptions.AST.TypesDontMatch;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.Attributes;
import SemanticAnalyzer.SymbolTable.SymbolTable;
import SemanticAnalyzer.SymbolTable.Variable;

import java.util.Map;

/**
 * Clase representate una expresión unaria en nuestro AST
 * @author Yeumen Silva
 */

public class ExpUn extends ExpOp {

    ExpressionNode right;

    public ExpUn(String struct, String method) {
        super(struct, method);
    }

    public ExpUn(String struct, String method, Token operator) {
        super(struct, method, operator);
    }

    public ExpUn(String struct, String method, Token operator, ExpressionNode right) {
        super(struct, method, operator);
        this.right = right;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public ExpressionNode getRight() {
        return right;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */
    @Override
    public String toJson(int tabs) {

            String json = addtabs(tabs) + "{\n";
            json += addtabs(tabs+1) + "\"nombre\": \"" + "ExpUn" + "\",\n";
            json += addtabs(tabs+1) + "\"operator\": \"" + getOperator().getLexeme() + "\",\n";
            json += addtabs(tabs+1) + "\"right\": " + right.toJson(tabs+1) + "\n";
            json += addtabs(tabs) + "}\n";
            return json;
    }

    /**
     * Método que consolida un nodo ExpUn
     * @param ast AST que contiene la información
     * @autor Yeumen Silva
     */

    @Override
    public void consolidate(AST ast) {
        if(right.getConsolidated()  == false){
            right.consolidate(ast);
        }
        switch (getOperator().getLexeme()) {
            case "-", "+", "--", "++":
                //Si el tipo del dato es int, se mantiene el tipo
                if(right.getType().equals("Int"))
                    setType(right.getType());
                else
                    throw new NeedToBeInt(this.getToken());
                break;
            case "!":
                if(right.getType().equals("Bool")) {
                    setType(right.getType());
                }
                else{
                    throw new InvalidLogicalComparation(this.getToken());
                }
                break;
            default:
                setType("nil");
                break;
        }
        this.setConsolidated(true);
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
        String textCode = "";
        textCode += this.right.generateCode(codeGenerator);
        String operator = this.getOperator().getLexeme();

        switch (operator){
            case "--":
                textCode += "\taddiu $v0,$v0, -1 #--\n";
            case "++":
                textCode += "\taddiu $v0,$v0, 1 #++\n";
                break;
            case "!":
                textCode += "\txori $v0,$v0,1 #!\n";
        }

        // Acá lo que hacemos es asignarle a la variable/atributo que cambiamos ese valor (es practicamente el codigo de asignación)
        SymbolTable symbolTable = codeGenerator.getSymbolTable();
        boolean isAttribute = false;

        int currentAttributePos = 0;
        int currentVariablePos = 0; // Con esto se obtiene la posicion de la variable en la lista de variables declaradas
        if(!((IdNode)right).isChained()) { // Si no tiene encadenado:
            String rightName = right.getToken().getLexeme();

            // Buscamos la posición de la variable en la lista de variables declaradas
            Map<String, Variable> variableList = symbolTable.getStructMethodDeclaredVariables(this.getStruct(), this.getMethod());
            boolean variableFound = false; // Este booleano sirve para cuando estamos en un constructor saber si encontramos la variable declarada
            // Recorro la lista de todas las variables
            for (String varName : variableList.keySet()){
                if (varName.equals(rightName)){
                    variableFound = true;
                    break;
                }
                currentVariablePos += 1;
            }

            // Buscamos si es un atributo
            if ( !variableFound) { // si no encontramos la variable declarada
                Map<String, Attributes> attributeList = symbolTable.getStructAttributes(this.getStruct());
                // Recorro la lista de todos los atributos hasta encontrar el atributo buscado
                for (String attrName : attributeList.keySet()) {
                    if (attrName.equals(rightName)){
                        isAttribute = true;
                        break;
                    }
                    currentAttributePos += 1;
                }
            }
        } else { // Esto pasa cuando si tiene encadenado
            // TODO: asignación con encadenado
            textCode += right.generateCode(codeGenerator);
        }

        if (!isAttribute || this.getMethod().equals(".")) { // Chequeamos que sea un constructor porque sino no anda xd
            // Meto el valor asignado de la variable en la posicion correcta del stack
            int variableStackPos = -4 * (currentVariablePos + 1);
            textCode += "\tsw $v0, " + variableStackPos + "($fp)\t# Meto el valor asignado de la variable en la posicion correcta del stack\n";
        } else {
            // Meto el valor asignado del atributo en la posicion correcta del heap
            int attributeStackPos = 4 * (currentAttributePos + 1);
            textCode += "\tlw $t0, 8($fp)\t# Cargamos el CIR en $t0\n"+
                    "\tsw $v0, " + attributeStackPos + "($t0)\t# Meto el valor asignado del atributo en la posicion correcta del heap\n"+
                    "\tla $v0, ($t0)\t# Cargamos el CIR en $v0 para ser guardado por funciones\n";//TODO: si en vez de un atributo, ponemos una variable, puede que se rompa xd
        }

        return textCode;



    }
}
