package SemanticAnalyzer.AST;

/**
 * Clase representate la asignación en nustro AST
 * @author Yeumen Silva
 */

public class AsignationNode extends SentenceNode {

    Node left;
    Node right;

    public AsignationNode(SemanticContext currentContext){
        super(currentContext);
    }

}
