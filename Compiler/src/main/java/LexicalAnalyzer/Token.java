package LexicalAnalyzer;

public class Token {

    private int row, column;

    private String token, lexeme;

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public String getToken() {
        return token;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }
}
