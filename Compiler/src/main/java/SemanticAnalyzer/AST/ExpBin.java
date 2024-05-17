package SemanticAnalyzer.AST;


import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión binaria en nuestro AST
 * @author Yeumen Silva
 */

public class ExpBin extends ExpOp {

    ExpressionNode left;

    ExpressionNode right;

    public ExpBin(String struct, String method) {
        super(struct, method);
    }

    public ExpBin(String struct, String method, Token operator) {
        super(struct, method, operator);
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

    public ExpressionNode getRight() {
        return right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
