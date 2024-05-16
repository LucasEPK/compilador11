package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión unaria en nustro AST
 * @author Yeumen Silva
 */

public class ExpUn extends ExpressionNode {

    Node right;


    public ExpUn(Token token, String struct, String method) {
        super(token, struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
