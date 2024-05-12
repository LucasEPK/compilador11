package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un ToDo nustro AST
 * @author Yeumen Silva
 */

public class PrimaryNode extends AbstractSentenceNode{

    PrimaryNode right;

    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public PrimaryNode(Token token, String struct, String method) {
        super(token, struct, method);
    }
}
