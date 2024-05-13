package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class AbstractCallNode extends AbstractPrimaryNode{
    public AbstractCallNode(Token token, String struct, String method, String name) {
        super(token, struct, method, name);
    }
}
