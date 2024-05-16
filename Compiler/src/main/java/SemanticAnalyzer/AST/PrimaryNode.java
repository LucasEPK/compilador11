package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un primario
 * @author Yeumen Silva
 */

public class PrimaryNode extends Node{

    PrimaryNode right;

    public PrimaryNode(Token token, String struct, String method) {
        super(token, struct, method);
    }
}
