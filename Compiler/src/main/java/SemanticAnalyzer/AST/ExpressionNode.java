package SemanticAnalyzer.AST;

/**
 * Clase abstracta representate una expresión en general
 * @author Yeumen Silva
 */

public abstract class ExpressionNode extends  SentenceNode implements Commons {

    String operator = null;

    public ExpressionNode(String struct, String method) {
        super(struct, method);
    }
}
