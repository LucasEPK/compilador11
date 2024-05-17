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

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
