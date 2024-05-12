package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public abstract class AbstractExpressionNode extends AbstractSentenceNode {
    public AbstractExpressionNode(Token token, String struct, String method,String name) {
        super(token, struct, method,name);
    }
}
