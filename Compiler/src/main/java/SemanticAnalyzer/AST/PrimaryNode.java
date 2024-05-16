package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un primario
 * @author Yeumen Silva
 */

public class PrimaryNode extends Node{

    PrimaryNode right;

    public PrimaryNode(Token token) {
        //TODO: agregar un tipo a primo en vez de poner null
        super(token, null);
    }
}
