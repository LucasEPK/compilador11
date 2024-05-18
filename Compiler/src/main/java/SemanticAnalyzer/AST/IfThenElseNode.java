package SemanticAnalyzer.AST;


import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate una sentencia if-else en nustro AST
 * @author Yeumen Silva
 */

public class IfThenElseNode extends  SentenceNode implements Commons {

    ExpressionNode ifNode;

    SentenceNode thenNode;

    SentenceNode elseNode;


    public IfThenElseNode(String struct, String method) {
        super(struct, method);
    }

    public void setIfNode(ExpressionNode ifNode) {
        this.ifNode = ifNode;
    }

    public void setThenNode(SentenceNode thenNode) {
        this.thenNode = thenNode;
    }

    public void setElseNode(SentenceNode elseNode) {
        this.elseNode = elseNode;
    }

    public ExpressionNode getIfNode() {
        return ifNode;
    }

    public SentenceNode getThenNode() {
        return thenNode;
    }

    public SentenceNode getElseNode() {
        return elseNode;
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
            json += addtabs(tabs+1) + "\"nombre\": \"" + "IfThenElse" + "\",\n";
            json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
            json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
            json += addtabs(tabs+1) + "\"Condición\": " + ifNode.toJson(tabs+1) + ",\n";
            json += addtabs(tabs+1) + "\"then\": " + thenNode.toJson(tabs+1) + ",\n";
            json += addtabs(tabs+1) + "\"else\": " + elseNode.toJson(tabs+1) + "\n";
            json += addtabs(tabs) + "}\n";
            return json;

    }

    @Override
    public void consolidate(AST ast) {

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
