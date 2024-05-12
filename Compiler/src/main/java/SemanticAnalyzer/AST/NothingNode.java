package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;

public class NothingNode extends AbstractSentenceNode{
    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public NothingNode(Token token, String struct, String method) {
        super(token, struct, method,"NothingNode");
    }
}
