package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class ArrayNode extends PrimaryNode{
    private int length;

    public ArrayNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public ArrayNode(String struct, String method, Token token, String type) {
        super(struct, method, token, type);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */
    @Override
    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "Array" + "\",\n";
        json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
        json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
        json += addtabs(tabs+1) + "\"value\": \"" + getToken().getLexeme() + "\",\n";
        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\",\n";
        json += addtabs(tabs+1) + "\"length\": \"" + getLength() + "\"\n";
        json += addtabs(tabs) + "},\n";
        return json;
    }

    @Override
    public void consolidate() {
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
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
