package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class LiteralNode extends  AbstractPrimaryNode{
    public LiteralNode(Token token, String struct, String method,String type) {
        super(token, struct, method,"LiteralNode");
        this.setType(type);
    }
}
