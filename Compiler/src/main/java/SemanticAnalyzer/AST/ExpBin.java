package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión binaria en nustro AST
 * @author Yeumen Silva
 */

public class ExpBin extends ExpressionNode {

    Node left;

    Node right;

    public ExpBin(Token token, String struct, String method) {
        super(token, struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
