package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

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
        //Si el tipo es string y tiene comillas no le agrego comillas
        if(isStringType(getType())) {
            json += addtabs(tabs + 1) + "\"value\": " + getToken().getLexeme() + ",\n";
        }else {
            json += addtabs(tabs+1) + "\"value\": \"" + getToken().getLexeme() + "\",\n";
        }

        json += addtabs(tabs+1) + "\"type\": \"" + getType() + "\"\n";
        json += addtabs(tabs) + "}\n";
        return json;
    }

    /**
     * Método que consolida un nodo literal
     * @param ast AST que contiene la información
     * @autor Lucas Moyano
     */

    @Override
    public void consolidate(AST ast) {
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

    /**
     * Método que verifica si el tipo es de tipo String y si empieza y termina con comillas
     * @param type Tipo a verificar
     * @return true si es de tipo String y empieza y termina con comillas, false en caso contrario
     */
    public boolean isStringType(String type){
        //Verifico si es de tipo String y si empieza y termina con comillas
        if(type.equals("Str") && getToken().getLexeme().charAt(0) == '"' && getToken().getLexeme().charAt(getToken().getLexeme().length()-1) == '"'){
            return true;
        }
        return false;
    }

    @Override
    public String generateCode() {
        String textCode = "";

        switch (getType()){
            case "Int":
                textCode += "\tli $v0, " + getToken().getLexeme() + "\n";
                break;
            case "Bool":
                if(getToken().getLexeme().equals("true")){
                    textCode += "\tli $v0, 1\n";
                }else{
                    textCode += "\tli $v0, 0\n";
                }
                break;
            case "Char":
                /*
                ESTO SE HARIA CON EL ARRAY Y VA EN .data
                textCode += "\t Char_" + getToken().getRow() + ": .asciiz " + getToken().getLexeme() + "\n";
                 */
                textCode += "\t la $v0, Char_" + getToken().getRow() + "\n";
                break;
            case "Str":
                /*
                ESTO SE HARIA CON EL ARRAY Y VA EN .data
                textCode += "\t Str_" + getToken().getRow() + ": .asciiz " + getToken().getLexeme() + "\n";
                 */
                textCode += "\t la $v0, Str_" + getToken().getRow() + "\n";
                break;
        }
        return textCode;
    }
}
