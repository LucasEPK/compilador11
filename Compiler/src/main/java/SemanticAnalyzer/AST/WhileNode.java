package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una sentencia while en nustro AST
 * @author Yeumen Silva
 */

public class WhileNode extends AbstractSentenceNode {

    AbstractSentenceNode whileNode;

    AbstractSentenceNode doNode;


    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @author Yeumen Silva
     */
    public WhileNode(Token token, String struct, String method) {
        super(token, struct, method);
    }

    /**
     * Constructor que asigna token, struct y método
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @param whileNode Nodo expresion condicion while
     * @param doNode sentencia dentro de node
     * @author Yeumen Silva
     */
    public WhileNode(Token token, String struct, String method, ExpressionNode whileNode, AbstractSentenceNode doNode) {
        super(token, struct, method,"WhileNode");
        this.whileNode = whileNode;
        this.doNode = doNode;
    }
}
