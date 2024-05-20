package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa los id y structsIds en el AST
 * @author Lucas Moyano
 * */
public class IdNode extends PrimaryNode{
    private List<ExpressionNode> arguments = new ArrayList<ExpressionNode>();

    private IdType idType;

    public IdNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public void setArguments(List<ExpressionNode> arguments) {
        this.arguments = arguments;
    }

    /**
     * Método que convierte un objeto a un string en formato JSON
     * @param tabs Cantidad de tabulaciones a agregar al archivo JSON
     * @return string en formato JSON
     */
    @Override
    public String toJson(int tabs) {
        // TODO: fijarse a la derecha si hay algo, si lo hay imprimirlo
        String json = addtabs(tabs) + "{\n";
        json += addtabs(tabs+1) + "\"nombre\": \"" + "Id" + "\",\n";
        json += addtabs(tabs+1) + "\"value\": \"" + getToken().getLexeme() + "\",\n";
        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\",\n";
        json += addtabs(tabs+1) + "\"arguments\": [\n";
        int size = 0;
        if (arguments != null) {
            for (ExpressionNode argument : arguments) {
                json += argument.toJson(tabs+2);
                if(size < arguments.size()-1){
                    json += ",\n";
                }
                size++;
            }
        }
        json = json.substring(0,json.length()-1);
        json += "\n";
        json += addtabs(tabs+1) + "]\n";
        json += addtabs(tabs) + "}\n";
        return json;
    }

    @Override
    public void consolidate(AST ast) {
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
