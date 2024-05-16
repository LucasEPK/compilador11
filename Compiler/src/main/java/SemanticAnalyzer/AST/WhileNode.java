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

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
