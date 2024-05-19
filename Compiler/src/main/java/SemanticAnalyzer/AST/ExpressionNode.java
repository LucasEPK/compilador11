package SemanticAnalyzer.AST;

/**
 * Clase abstracta representate una expresión en general
 * @author Yeumen Silva
 */

public abstract class ExpressionNode extends  SentenceNode implements Commons {

    public ExpressionNode(String struct, String method) {
        super(struct, method);
    }

    public ExpressionNode(String struct, String method, String type) {
        super(struct, method);
        this.setType(type);
    }

}
