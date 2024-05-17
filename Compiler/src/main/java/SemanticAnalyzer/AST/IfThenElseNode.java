package SemanticAnalyzer.AST;


/**
 * Clase representate una sentencia if-else en nustro AST
 * @author Yeumen Silva
 */

public class IfThenElseNode extends  SentenceNode implements Commons {

    ExpressionNode ifNode;

    SentenceNode thenNode;

    SentenceNode elseNode;


    public IfThenElseNode(String struct, String method) {
        super(struct, method);
    }

    public void setIfNode(ExpressionNode ifNode) {
        this.ifNode = ifNode;
    }

    public void setThenNode(SentenceNode thenNode) {
        this.thenNode = thenNode;
    }

    public void setElseNode(SentenceNode elseNode) {
        this.elseNode = elseNode;
    }

    public ExpressionNode getIfNode() {
        return ifNode;
    }

    public SentenceNode getThenNode() {
        return thenNode;
    }

    public SentenceNode getElseNode() {
        return elseNode;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
