package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class AsignationVariableCallNode extends  AbstractSentenceNode{


    //booleano para saber si es o no array
    boolean isArray = false;

    //Como accedo a la posición

    AbstractSentenceNode accesPos;

    //Nodo que llama
    AsignationVariableCallNode callNode;



    public AsignationVariableCallNode(Token token, String struct, String method) {
        super(token, struct, method,"AsignationVariableCallNode");
    }

    /**
     * Método que setea si es una array o no
     * @param isArray booleano
     */

    public void setIsArray(boolean isArray) {
        this.isArray = isArray;
    }

    /**
     * Setea el acceso a la pos
     * @param accesPos Nodo Sentencia
     */

    public void setAccesPos(AbstractSentenceNode accesPos) {
        this.accesPos = accesPos;
    }

    /**
     * Método que devuelve el nodo de la llamada
     * @return Nodo de asignacion
     */
    public AsignationVariableCallNode getCallNode() {
        return callNode;
    }

    /**
     * Método que setea el call node
     * @param callNode Nodo con datos de asignacion
     */

    public void setCallNode(AsignationVariableCallNode callNode) {
        this.callNode = callNode;
    }
}
