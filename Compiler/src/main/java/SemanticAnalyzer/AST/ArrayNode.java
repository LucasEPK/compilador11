package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class ArrayNode extends PrimaryNode{
    private int length;

    public ArrayNode(String struct, String method, Token token) {
        super(struct, method, token);
    }

    public ArrayNode(String struct, String method, Token token, String type) {
        super(struct, method, token, type);
    }
}
