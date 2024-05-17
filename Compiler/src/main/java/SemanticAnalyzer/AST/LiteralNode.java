package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un literal en nuestro AST
 * @author Lucas Moyano
 */

public class LiteralNode extends ExpressionNode{

    public LiteralNode(String struct, String method, Token token){
        super(struct, method, token);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {
    }
}
