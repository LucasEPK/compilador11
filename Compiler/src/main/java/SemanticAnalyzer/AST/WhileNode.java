package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una sentencia while en nustro AST
 * @author Yeumen Silva
 */

public class WhileNode extends AbstractSentenceNode {

    ExpressionNode whileNode;

    AbstractSentenceNode doNode;


    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public WhileNode(Token token, String struct, String method) {
        super(token, struct, method);
    }
}
