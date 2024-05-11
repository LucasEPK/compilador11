package SemanticAnalyzer.AST;

import SemanticAnalyzer.SymbolTable.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase representate ToDo
 * @author Yeumen Silva
 */

public class BlockNode extends SentenceNode {

    private List<SentenceNode> sentenceList = new ArrayList<SentenceNode>();

    /**
     * En este constructor se establece el contexto de la tabla de simbolos en donde está el bloque
     * @author Lucas Moyano
     * */
    public BlockNode(SymbolTable symbolTable) {
        // Con esto se agrega el currentStruct y currentMethod al contexto semantico del sentenceNode
        super(new SemanticContext(symbolTable.getCurrentStruct(), symbolTable.getCurrentMethod()));
    }

    /**
     * Esta función se encarga de elegir el tipo de sentencia que se va a agregar en la lista
     * después de eso la agrega a la sentenceList
     * @param sentenceType el tipo de sentencia que se quiere en String
     * @author Lucas Moyano
     * */
    public void addNewSentence(String sentenceType){
        SentenceNode newSentence = null;

        switch (sentenceType) {
            case "Asignation":
                newSentence = new AsignationNode(getCurrentContext());
            case "ExpBin":
                newSentence = new ExpBin(getCurrentContext());
            case "ExpUn":
                newSentence = new ExpUn(getCurrentContext());
            case "IfThenElse":
                newSentence = new IfThenElseNode(getCurrentContext());
            case "Return":
                newSentence = new ReturnNode(getCurrentContext());
            case "While":
                newSentence = new WhileNode(getCurrentContext());
            default:
                assert newSentence != null: "ERROR: el tipo de sentencia no existe";
        }

        sentenceList.add(newSentence);
    }

    public SemanticContext getCurrentContext() {
        return currentContext;
    }
}
