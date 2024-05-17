package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase abstracta que encapsula ExpUn y ExpBin del AST
 * @author Lucas Moyano
 * */
public abstract class ExpOp extends ExpressionNode{

    private Token operator;

    public ExpOp(String struct, String method) {
        super(struct, method);
    }

    public ExpOp(String struct, String method, Token operator) {
        super(struct, method);

        this.operator = operator;
    }

    public void setOperator(Token operator) {
        this.operator = operator;
    }

    public Token getOperator() {
        return operator;
    }
}
