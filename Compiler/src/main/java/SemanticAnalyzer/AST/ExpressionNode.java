package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate //ToDo
 * @author Yeumen Silva
 */

public abstract class ExpressionNode extends  SentenceNode implements Commons {

    String operator;

    public ExpressionNode(Token token, String struct, String method) {
        super(token, struct, method);
    }
}
