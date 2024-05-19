package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

public class ArrayNode extends PrimaryNode{

    private ExpressionNode length;

    public ArrayNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public ArrayNode(String struct, String method, Token token, String type) {
        super(struct, method, token, type);
    }

    public ExpressionNode getLength() {
        return length;
    }

    public void setLength(ExpressionNode length) {
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
        json += addtabs(tabs+1) + "\"value\": \"" + getToken().getLexeme() + "\",\n";
        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\",\n";
        json += addtabs(tabs+1) + "\"length\": \"" + length.toJson(tabs+1) + "\"\n";
        json += addtabs(tabs) + "}\n";
        return json;
    }

    @Override
    public void consolidate(AST ast) {

        if(this.length.getConsolidated() == false){
            this.length.consolidate(ast);
        }

        if(this.length.getType().equals("Int") == false){
            //ToDo
            //throw new ArrayLengthException(this.length.getToken());
        }

        //Si el tipo no es un tipo primitivo, es un error (Int,Str,Char,Bool)
        if(this.getType().equals("Int") == false && this.getType().equals("Str") == false &&
                this.getType().equals("Char") == false && this.getType().equals("Bool") == false){
            //ToDo
            //throw new NoPrimitiveType(this.getToken());
        }

    }


    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */

    @Override
    public String addtabs(int tabs) {
        String tabsString = "";
        for (int i = 0; i < tabs; i++
        ) {
            tabsString += "\t";
        }
        return tabsString;
    }
}
