package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un literal en nuestro AST
 * @author Lucas Moyano
 */

public class LiteralNode extends Operands {

    public LiteralNode(String struct, String method){
        super(struct, method);
    }

    public LiteralNode(String struct, String method, Token token, String type){
        super(struct, method, token, type);
    }


    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */

    @Override
    public String toJson(int tabs) {

        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "Literal" + "\",\n";
        json += addtabs(tabs+1) + "\"class\": \"" + getStruct() + "\",\n";
        json += addtabs(tabs+1) + "\"method\": \"" + getMethod() + "\",\n";
        json += addtabs(tabs+1) + "\"value\": " + getToken().getLexeme() + ",\n";
        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\"\n";
        json += addtabs(tabs) + "},\n";
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
