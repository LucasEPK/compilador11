package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate la asignación en nustro AST
 * @author Yeumen Silva
 */

public class AsignationNode extends SentenceNode implements Commons {

    Node left;
    Node right;


    public AsignationNode(Token token, String struct, String method) {
        super(token, struct, method);
    }

    @Override
    public void toJson(int tabs) {

    }

    @Override
    public void consolidate() {

    }
}
