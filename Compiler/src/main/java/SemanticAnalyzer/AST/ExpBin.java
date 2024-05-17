package SemanticAnalyzer.AST;


import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión binaria en nuestro AST
 * @author Yeumen Silva
 */

public class ExpBin extends ExpressionNode {

    ExpressionNode left;

    ExpressionNode right;

    public ExpBin(String struct, String method) {
        super(struct, method);
    }

    public ExpBin(String struct, String method, Token operator) {
        super(struct, method, operator);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
