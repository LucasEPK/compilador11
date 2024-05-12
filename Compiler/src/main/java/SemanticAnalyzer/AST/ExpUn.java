package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una expresión unaria en nustro AST
 * @author Yeumen Silva
 */

public class ExpUn extends ExpressionNode {

    AbstractSentenceNode expresion;

    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public ExpUn(Token token, String struct, String method) {
        super(token, struct, method);
    }

}
