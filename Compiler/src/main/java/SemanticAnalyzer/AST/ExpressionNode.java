package SemanticAnalyzer.AST;

/**
 * Clase representate //ToDo
 * @author Yeumen Silva
 */

public abstract class ExpressionNode extends  SentenceNode {

    String operator;

    public ExpressionNode(SemanticContext currentContext) {
        super(currentContext);
    }
}
