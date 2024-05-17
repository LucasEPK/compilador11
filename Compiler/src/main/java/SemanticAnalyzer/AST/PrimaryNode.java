package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un primario
 * @author Yeumen Silva
 */

public class PrimaryNode extends ExpressionNode{

    PrimaryNode right;

    public PrimaryNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
