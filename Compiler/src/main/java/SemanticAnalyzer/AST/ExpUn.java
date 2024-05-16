package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión unaria en nustro AST
 * @author Yeumen Silva
 */

public class ExpUn extends ExpressionNode {

    Node right;


    public ExpUn(String struct, String method) {
        super(struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
