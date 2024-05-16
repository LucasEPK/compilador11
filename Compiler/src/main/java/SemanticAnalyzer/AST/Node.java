package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una variable o un literal
 * @author Yeumen Silva
 */

public class Node implements Commons {

    Token token;

    String type;

    String lexeme;

    public Node(Token token, String type) {
        this.token = token;
        this.type = type;
        this.lexeme = token.getLexeme();
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
