package LexicalAnalyzer;

import Exceptions.LexicalException;

/**
 * Clase que será la encargada de almacenar los datos referidos
 * a nuestros tokens. Almacena: token, lexema, fila y columna
 * @throws LexicalException
 * @author Yeumen Silva
 */
public class Token {

    private int row, column;

    private String token, lexeme;

    public Token(String token, String lexeme, int row, int column){
        this.token = token;
        this.lexeme = lexeme;
        this.row = row;
        this.column = column;
    }

    /**
     * Este metodo sirve para cuando se muestra por pantalla un Token
     * @return Datos del token en forma de String
     * @author Lucas Moyano
     * */
    public String toString() {
        return "Token: " + this.token + ", Lexema: " + this.lexeme + ", Fila: " + this.row + ", Columna: " + this.column + "\n";
    }

    /**
     * Este metodo retorna el atributo token
     * @return Atributo token
     * @author Lucas Moyano
     * */

    public String getToken() {
        return token;
    }

    /**
     * Este metodo retorna el atributo lexeme
     * @return Atributo lexeme
     * @author Lucas Moyano
     * */

    public String getLexeme() {
        return lexeme;
    }

    /**
     * Este metodo permite modificar atributo column
     * @author Lucas Moyano
     * */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Este metodo permite modificar atributo row
     * @author Lucas Moyano
     * */

    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Este metodo permite modificar atributo token
     * @author Lucas Moyano
     * */

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Este metodo permite modificar atributo lexeme
     * @author Lucas Moyano
     * */
    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    /**
     * Este metodo retorna el atributo column
     * @return Atributo column
     * @author Lucas Moyano
     * */

    public int getColumn() {
        return column;
    }

    /**
     * Este metodo retorna el atributo row
     * @return Atributo row
     * @author Lucas Moyano
     * */
    public int getRow() {
        return row;
    }
}
