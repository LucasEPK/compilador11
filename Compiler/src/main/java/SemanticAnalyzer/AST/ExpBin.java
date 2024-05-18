package SemanticAnalyzer.AST;


import LexicalAnalyzer.Token;

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
                json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
                json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
                json += addtabs(tabs+1) + "\"left\": " + left.toJson(tabs+1) + ",\n";
                json += addtabs(tabs+1) + "\"operator\": \"" + getOperator().getLexeme() + "\",\n";
                json += addtabs(tabs+1) + "\"right\": " + right.toJson(tabs+1) + "\n";
                json += addtabs(tabs) + "}\n";
                return json;
    }

    @Override
    public void consolidate() {

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
