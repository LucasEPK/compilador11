package SemanticAnalyzer.AST;

/**
 * Clase representate una sentencia if-else en nustro AST
 * @author Yeumen Silva
 */

public class IfThenElseNode extends  SentenceNode {

    ExpressionNode ifNode;

    SentenceNode thenNode;

    SentenceNode elseNode;

    public IfThenElseNode(SemanticContext currentContext){
        super(currentContext);
    }
}
