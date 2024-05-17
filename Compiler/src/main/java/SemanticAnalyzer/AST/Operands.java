package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase abstracta que encapsula literales y primarios en el AST
 * @author Lucas Moyano
 * */

public abstract class Operands extends ExpressionNode{

    Token token;

    public Operands(String struct, String method){
        super(struct, method);
    }

    public Operands(String struct, String method, Token token){
        super(struct, method);

        this.token = token;
    }

    public Operands(String struct, String method, Token token, String type){
        super(struct, method, type);

        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
