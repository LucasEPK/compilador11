package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un ToDo en nustro AST
 * @author Yeumen Silva
 */

public class Node implements Commons {

    Token token;

    String struct;

    String method;

    public Node(Token token, String struct, String method) {
        this.token = token;
        this.struct = struct;
        this.method = method;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
