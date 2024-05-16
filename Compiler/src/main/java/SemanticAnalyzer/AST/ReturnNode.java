package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un return en nustro AST
 * @author Yeumen Silva
 */

public class ReturnNode extends SentenceNode implements Commons {

    ExpressionNode returnValueNode;


    public ReturnNode(Token token, String struct, String method) {
        super(token, struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
