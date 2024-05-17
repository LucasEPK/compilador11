package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public abstract class Operands extends ExpressionNode{

    Token token;

    public Operands(String struct, String method, Token token){
        super(struct, method);

        this.token = token;
    }

    public Operands(String struct, String method, Token token, String type){
        super(struct, method, type);

        this.token = token;
    }
}
