package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un return en nustro AST
 * @author Yeumen Silva
 */

public class ReturnNode extends AbstractSentenceNode {

    AbstractExpressionNode returnValueNode;

    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public ReturnNode(Token token, String struct, String method) {
        super(token, struct, method,"RetNode");
        this.setType("void");
        this.returnValueNode = null;
    }

    public ReturnNode(Token token, String struct, String method, AbstractExpressionNode returnValueNode) {
        super(token, struct, method,"RetNode");
        this.returnValueNode = returnValueNode;
        //ToDo this.setType(this.returnValueNode.getType());
    }
}
