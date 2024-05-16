package SemanticAnalyzer.AST;


/**
 * Clase representate una expresión binaria en nustro AST
 * @author Yeumen Silva
 */

public class ExpBin extends ExpressionNode {

    Node left;

    Node right;

    public ExpBin(String struct, String method) {
        super(struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
