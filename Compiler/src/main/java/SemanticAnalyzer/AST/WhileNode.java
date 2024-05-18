package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

/**
 * Clase representate una sentencia while en nustro AST
 * @author Yeumen Silva
 */

public class WhileNode extends SentenceNode {

    ExpressionNode whileNode;

    SentenceNode doNode;


    public WhileNode(String struct, String method) {
        super(struct, method);
    }

    public void setWhileNode(ExpressionNode whileNode) {
        this.whileNode = whileNode;
    }

    public void setDoNode(SentenceNode doNode) {
        this.doNode = doNode;
    }

    public ExpressionNode getWhileNode() {
        return whileNode;
    }

    public SentenceNode getDoNode() {
        return doNode;
    }

    @Override
    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "While" + "\",\n";
        json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
        json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
        json += addtabs(tabs+1) + "\"Condición\": " + whileNode.toJson(tabs+1) + ",\n";
        json += addtabs(tabs+1) + "\"do\": " + doNode.toJson(tabs+1) + "\n";
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
