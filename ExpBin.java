package SemanticAnalyzer.AST;


import Exceptions.SemanticExceptions.AST.DiferentPrimitiveTypesComparation;
import Exceptions.SemanticExceptions.AST.InvalidLogicalComparation;
import Exceptions.SemanticExceptions.AST.NeedToBeInt;
import Exceptions.SemanticExceptions.AST.TypesDontMatch;
import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate una expresión binaria en nuestro AST
 * @author Yeumen Silva
 */

public class ExpBin extends ExpOp {

    ExpressionNode left;

    ExpressionNode right;

    public ExpBin(String struct, String method) {
        super(struct, method);
    }

    public ExpBin(String struct, String method, Token operator) {
        super(struct, method, operator);
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */

    @Override
    public String toJson(int tabs) {

                String json = addtabs(tabs) + "{\n";
                json += addtabs(tabs+1) + "\"nombre\": \"" + "ExpBin" + "\",\n";
                json += addtabs(tabs+1) + "\"left\": " + left.toJson(tabs+1) + ",\n";
                json += addtabs(tabs+1) + "\"operator\": \"" + getOperator().getLexeme() + "\",\n";
                json += addtabs(tabs+1) + "\"right\": " + right.toJson(tabs+1) + "\n";
                json += addtabs(tabs) + "}\n";
                return json;
    }

    /**
     * Método que consolida un nodo ExpBin
     * @param ast AST a consolidar
     */
    @Override
    public void consolidate(AST ast) {

        if(this.left.getConsolidated() == false){
            this.left.consolidate(ast);
        }

        if(this.right.getConsolidated() == false){
            this.right.consolidate(ast);
        }



        switch (this.getOperator().getLexeme()){

            case "||", "&&":

                //Si cualquiera de los dos no es un booleano, es un error
                if(!this.left.getType().equals("Bool") || !this.right.getType().equals("Bool")){
                    throw new InvalidLogicalComparation(this.getToken());
                }
                this.setType("Bool");
                break;

            case "==", "!=":

                //Debemos verificar que si los tipos son distintos, ninguno sea primitivo
                //Verifico si los tipos son distintos
                if(!this.left.getType().equals(this.right.getType())){
                    //Si alguno de los dos es primitivo, es un error
                    if(ast.isPrimitive(this.left.getType()) || ast.isPrimitive(this.right.getType())){
                        throw new DiferentPrimitiveTypesComparation(this.getToken());
                    }
                }
                this.setType("Bool");
                break;

            case "<", ">", "<=", ">=":

                //Si cualquiera de los dos no es un entero, es un error
                if(!this.left.getType().equals("Int") || !this.right.getType().equals("Int")){
                    throw new NeedToBeInt(this.getToken());
                }
                this.setType("Bool");
                break;

            case "*", "/", "%", "+", "-":

                //Si cualquiera de los dos no es un entero, es un error
                if(!this.left.getType().equals("Int") || !this.right.getType().equals("Int")){
                    throw new NeedToBeInt(this.getToken());
                }
                this.setType("Int");
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
    public String generateCode() {
        String textCode = "";
        textCode += this.left.generateCode();
        textCode += this.right.generateCode();

        String operator = this.getToken().getLexeme();

        if (operator.equals("*") ||  operator.equals("/") || operator.equals("%") || operator.equals("+") || operator.equals("-")) {
            textCode += "\tli $v0, "+this.left.getToken().getLexeme()+"\n" +
                    "\tli $t9, "+this.right.getToken().getLexeme()+"\n" +
                    "\tpush\n";
        }

        switch (operator) {
            case "+":
                textCode += "\tjal default_sum\n";
                break;
            case "-":
                textCode += "\tjal default_sub\n";
                break;
            case "*":
                textCode += "\tjal default_mul\n";
                break;
            case "/":
                textCode += "\tjal default_div\n";
                break;
            case "%":
                textCode += "\tjal default_module\n";
                break;
            default:


        }

        return textCode;
    }

}
