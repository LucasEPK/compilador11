package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un ToDo en nustro AST
 * @author Yeumen Silva
 */

public class Node extends SentenceNode implements Commons {


    public Node(Token token, String struct, String method) {
        super(token, struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
