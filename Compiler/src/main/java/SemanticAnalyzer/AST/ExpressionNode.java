package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate //ToDo
 * @author Yeumen Silva
 */

public class ExpressionNode extends  AbstractExpressionNode {

    AbstractSentenceNode left;
    AbstractSentenceNode rigth;


    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public ExpressionNode(Token token, String struct, String method) {
        super(token, struct, method,"ExpressionNode");
    }

    public ExpressionNode(Token token, String struct, String method, AbstractSentenceNode left, AbstractSentenceNode rigth) {
        super(token, struct, method,"ExpressionNode");
        this.left = left;
        this.rigth = rigth;
    }
}
