package SemanticAnalyzer.AST;


import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión unaria en nuestro AST
 * @author Yeumen Silva
 */

public class ExpUn extends ExpOp {

    ExpressionNode right;

    public ExpUn(String struct, String method) {
        super(struct, method);
    }

    public ExpUn(String struct, String method, Token operator) {
        super(struct, method, operator);
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
