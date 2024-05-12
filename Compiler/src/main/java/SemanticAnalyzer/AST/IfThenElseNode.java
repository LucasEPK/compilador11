package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una sentencia if-else en nustro AST
 * @author Yeumen Silva
 */

public class IfThenElseNode extends  AbstractSentenceNode {

    ExpressionNode ifNode;

    AbstractSentenceNode thenNode;

    AbstractSentenceNode elseNode;

    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public IfThenElseNode(Token token, String struct, String method) {
        super(token, struct, method);
    }
}
