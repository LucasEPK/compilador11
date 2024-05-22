package SemanticAnalyzer.AST;


import Exceptions.SemanticExceptions.AST.InvalidNilPrimitive;
import Exceptions.SemanticExceptions.AST.TypesDontMatch;
import Exceptions.SemanticExceptions.AST.VoidAsignation;
import SemanticAnalyzer.SymbolTable.SymbolTable;

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
}
