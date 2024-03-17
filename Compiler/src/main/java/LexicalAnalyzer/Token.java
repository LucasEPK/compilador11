package LexicalAnalyzer;

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
     * @author Lucas Moyano
     * */
    public String toString() {
        return "Token: " + getToken() + ", Lexema: " + getLexeme() + ", Fila: " + getRow() + ", Columna: " + getColumn() + "\n";
    }

    public String getToken() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
