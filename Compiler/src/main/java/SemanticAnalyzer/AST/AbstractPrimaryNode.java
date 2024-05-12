package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public abstract class AbstractPrimaryNode extends AbstractExpressionNode {
    public AbstractPrimaryNode(Token token, String struct, String method,String name) {
        super(token, struct, method,name);
    }
}
