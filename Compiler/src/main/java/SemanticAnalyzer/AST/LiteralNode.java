package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un literal en nuestro AST
 * @author Lucas Moyano
 */

public class LiteralNode extends ExpressionNode{

    public LiteralNode(String struct, String method, Token token, Types type){
        super(struct, method, token, type);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {
    }
}
