package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión binaria en nustro AST
 * @author Yeumen Silva
 */

public class ExpBin extends ExpressionNode {

    AbstractSentenceNode left;

    AbstractSentenceNode right;

    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public ExpBin(Token token, String struct, String method) {
        super(token, struct, method);
    }
}
