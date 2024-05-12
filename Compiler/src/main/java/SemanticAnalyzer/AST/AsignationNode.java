package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate la asignación en nustro AST
 * @author Yeumen Silva
 */

public class AsignationNode extends AbstractSentenceNode {

    AbstractSentenceNode left;
    AbstractSentenceNode right;

    /**
     * Constructor que asigna token, struct y método
     * @param token token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */

    public AsignationNode(Token token, String struct, String method) {
        super(token, struct, method);
    }
}
