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

}
