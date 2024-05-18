package SemanticAnalyzer.AST;

/**
 * Clase abstracta representate una expresión en general
 * @author Yeumen Silva
 */

public abstract class ExpressionNode extends  SentenceNode implements Commons {

    private String type = "void";

    public ExpressionNode(String struct, String method) {
        super(struct, method);
    }

    public ExpressionNode(String struct, String method, String type) {
        super(struct, method);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
