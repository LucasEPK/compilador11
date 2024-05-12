package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate un ToDo en nustro AST
 * @author Yeumen Silva
 */

public abstract class AbstractSentenceNode extends ASTNode {


    /**
     * Constructor que asigna token, struct y método
     * @param token token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */

    public AbstractSentenceNode(Token token, String struct, String method) {
        super(token, struct, method);
    }

    /**
     * Constructor que asigna token, struct y método
     * @param token token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @param nodeName nombre del nodo
     * @author Yeumen Silva
     */


    public AbstractSentenceNode(Token token, String struct, String method, String nodeName){
        super(token,struct,method,nodeName);
    }
}
