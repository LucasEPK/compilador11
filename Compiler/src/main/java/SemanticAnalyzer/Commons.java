package SemanticAnalyzer;

import LexicalAnalyzer.Token;

public abstract class Commons {

    //Token que almacena, linea, fila, columna, token y lexema
    protected Token token;

    /**
     * Método que setea el token
     * @param token
     */

    public void setToken(Token token) {
        this.token = token;
    }

    protected String toJson(int tabs){
        return null;
    }

    protected String addtabs(int tab){
        String stringWithTabs = "";
        for (int i = 0; i < tab; i++ ){
            stringWithTabs = stringWithTabs + "\t";
        }
        return stringWithTabs;
    }
}
