package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

/**
 * Clase representate una sentencia if-else en nustro AST
 * @author Yeumen Silva
 */

public class IfThenElseNode extends  AbstractSentenceNode {

    AbstractExpressionNode ifExpressionNode;

    AbstractSentenceNode thenNode;

    AbstractSentenceNode elseNode;

    /**
     * Constructor que asigna token, struct y método
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @author Yeumen Silva
     */
    public IfThenElseNode(Token token, String struct, String method) {
        super(token, struct, method);
    }

    /**
     * Constructor que asigna token, struct y método
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @param expressionNode expresion del if
     * @param thenNode sentencia deñ if
     * @param elseNode sentencia else
     * @author Yeumen Silva
     */

    public IfThenElseNode(Token token, String struct, String method
            , AbstractExpressionNode expressionNode, AbstractSentenceNode thenNode, AbstractSentenceNode elseNode){
        super(token,struct,method,"IfThenElseNode");
        this.ifExpressionNode = expressionNode;
        this.thenNode = thenNode;
        this.elseNode = elseNode;

    }

    /**
     * Constructor que asigna token, struct y método
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     * @param expressionNode expresion del if
     * @param thenNode sentencia deñ if
     * @author Yeumen Silva
     */


    public IfThenElseNode(Token token, String struct, String method
            , AbstractExpressionNode expressionNode, AbstractSentenceNode thenNode){
        super(token,struct,method,"IfThenElseNode");
        this.ifExpressionNode = expressionNode;
        this.thenNode = thenNode;

    }
}
