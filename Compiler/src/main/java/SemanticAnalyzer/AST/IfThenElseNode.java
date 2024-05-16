package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una sentencia if-else en nustro AST
 * @author Yeumen Silva
 */

public class IfThenElseNode extends  SentenceNode implements Commons {

    ExpressionNode ifNode;

    SentenceNode thenNode;

    SentenceNode elseNode;


    public IfThenElseNode(Token token, String struct, String method) {
        super(token, struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
