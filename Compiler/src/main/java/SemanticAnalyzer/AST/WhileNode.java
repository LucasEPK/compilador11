package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una sentencia while en nustro AST
 * @author Yeumen Silva
 */

public class WhileNode extends SentenceNode {

    ExpressionNode whileNode;

    SentenceNode doNode;


    public WhileNode(Token token, String struct, String method) {
        super(token, struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
