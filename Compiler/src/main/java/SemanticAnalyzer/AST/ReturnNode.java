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

    public ExpressionNode setReturnValueNode(String type) {
        switch (type) {
            case "ExpBin":
                this.returnValueNode = new ExpBin(getStruct(), getMethod());
                break;
            case "ExpUn":
                this.returnValueNode = new ExpUn(getStruct(), getMethod());
                break;
            case "Primary":
                this.returnValueNode = new PrimaryNode(getStruct(), getMethod(), null);
                break;
            case "Literal":
                this.returnValueNode = new LiteralNode(getStruct(), getMethod(), null, null);
                break;
            default:
                assert this.returnValueNode != null: "ERROR: el tipo de expresion no existe";
        }

        return this.returnValueNode;
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
