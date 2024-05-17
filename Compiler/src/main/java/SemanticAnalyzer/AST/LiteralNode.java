package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un literal en nuestro AST
 * @author Lucas Moyano
 */

public class LiteralNode extends Operands {

    public LiteralNode(String struct, String method){
        super(struct, method);
    }

    public LiteralNode(String struct, String method, Token token, String type){
        super(struct, method, token, type);
    }

    @Override
    public String toJson(int tabs) {
        return null;
    }

    @Override
    public void consolidate() {
    }

    @Override
    public String addtabs(int tabs) {
        return null;
    }
}
