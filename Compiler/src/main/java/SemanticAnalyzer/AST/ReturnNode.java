package SemanticAnalyzer.AST;


/**
 * Clase representate un return en nuestro AST
 * @author Yeumen Silva
 */

public class ReturnNode extends SentenceNode implements Commons {

    ExpressionNode returnValueNode = null;


    public ReturnNode(String struct, String method) {
        super(struct, method);
    }

    public void setReturnValueNode(ExpressionNode returnValueNode) {
        this.returnValueNode = returnValueNode;
    }

    public ExpressionNode getReturnValueNode() {
        return returnValueNode;
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
