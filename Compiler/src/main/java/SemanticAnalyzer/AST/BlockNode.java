package SemanticAnalyzer.AST;

import LexicalAnalyzer.Token;
import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase representate ToDo
 * @author Yeumen Silva
 */

public class BlockNode extends AbstractSentenceNode {

    private List<AbstractSentenceNode> sentenceList = new ArrayList<AbstractSentenceNode>();


    /**
     * Constructor que asigna token, struct y método
     *
     * @param token  token que contiene, lexema, fila y columna
     * @param struct nombre del struct al que pertenece
     * @param method nombre del método al que pertenece
     */
    public BlockNode(Token token, String struct, String method, ArrayList<AbstractSentenceNode> sentenceList) {
        super(token, struct, method);
        this.sentenceList = sentenceList;
    }
}

