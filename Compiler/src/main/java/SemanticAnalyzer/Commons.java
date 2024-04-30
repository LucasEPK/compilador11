package SemanticAnalyzer;

import LexicalAnalyzer.Token;

/**
 * Clase abstracta que representa los atributos y métodos
 * comunes de nuestra implementación
 * @author Yeumen Silva
 */

public abstract class Commons {

    //Token que almacena, linea, fila, columna, token y lexema
    protected Token token;

    /**
     * Método que setea el token
     * @param token
     * @author Yeumen Silva
     */

    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Método que devuelve el token
     * @return Token
     * @author Yeumen Silva
     */

    public Token getToken() {
        return token;
    }

    /**
     * Método que convierte en formato json
     * @param tabs cantida de tabs de identación
     * @return string con formato Json
     * @author Yeumen Silva
     */

    protected String toJson(int tabs){
        return null;
    }

    /**
     * Método que dada una cantida de tabs, devuelve un string
     * con tabs
     * @param tab int que representa cantidad de tabs
     * @return string con tabs
     * @author Yeumen Silva
     */

    protected String addtabs(int tab){
        String stringWithTabs = "";
        for (int i = 0; i < tab; i++ ){
            stringWithTabs = stringWithTabs + "\t";
        }
        return stringWithTabs;
    }
}
