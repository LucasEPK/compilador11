package SemanticAnalyzer.AST;

/**
 * Clase representate una sentencia while en nustro AST
 * @author Yeumen Silva
 */

public class WhileNode extends SentenceNode {

    ExpressionNode whileNode;

    SentenceNode doNode;


    public WhileNode(String struct, String method) {
        super(struct, method);
    }

    public void setWhileNode(ExpressionNode whileNode) {
        this.whileNode = whileNode;
    }

    public void setDoNode(SentenceNode doNode) {
        this.doNode = doNode;
    }

    public ExpressionNode getWhileNode() {
        return whileNode;
    }

    public SentenceNode getDoNode() {
        return doNode;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
