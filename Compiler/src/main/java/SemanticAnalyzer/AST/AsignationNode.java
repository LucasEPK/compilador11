package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate la asignación en nustro AST
 * @author Yeumen Silva
 */

public class AsignationNode extends AbstractSentenceNode {

    AsignationVariableCallNode left;
    AbstractExpressionNode right;

    /**
     * Constructor que asigna token, struct y método
     * @param token token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @author Yeumen Silva
     */

    public AsignationNode(Token token, String struct, String method) {
        super(token, struct, method,"AsignationNode");
    }

    /**
     * Constructor que asigna token, struct y método
     * @param token token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @param left Nodo izquierdo de asignación
     * @param right Nodo derecho
     */

    public AsignationNode(Token token, String struct, String method, AsignationVariableCallNode left, AbstractExpressionNode right) {
        super(token, struct, method,"AsignationNode");
        this.left = left;
        this.right = right;
        this.setType("void");
    }
}
