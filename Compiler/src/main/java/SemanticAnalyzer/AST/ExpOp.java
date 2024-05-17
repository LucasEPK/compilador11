package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public abstract class ExpOp extends ExpressionNode{

    private Token operator;

    public ExpOp(String struct, String method, Token operator) {
        super(struct, method);

        this.operator = operator;
    }
}
