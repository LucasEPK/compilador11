package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un ToDo nustro AST
 * @author Yeumen Silva
 */

public class PrimaryNode extends AbstractPrimaryNode{

    AbstractCallNode callNode;

    PrimaryNode chainedNode;

    String lastChainedType;

    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public PrimaryNode(Token token, String struct, String method) {
        super(token, struct, method,"PrimaryNode");
    }

    public PrimaryNode(Token token, String struct, String method, AbstractCallNode callNode, PrimaryNode chainedNode) {
        super(token, struct, method,"PrimaryNode");
        this.callNode = callNode;
        this.chainedNode = chainedNode;
        this.setType(callNode.getType());
    }

    public PrimaryNode(Token token, String struct, String method, AbstractCallNode callNode) {
        super(token, struct, method,"PrimaryNode");
        this.callNode = callNode;
        this.setType(callNode.getType());
    }
}
