package SemanticAnalyzer.AST;

/**
 * Clase representate un ToDo en nustro AST
 * @author Yeumen Silva
 */

public abstract class SentenceNode {

    SemanticContext currentContext;

    /**
     * En este constructor se pasa el contexto de la tabla de simbolos desde el bloque
     * @author Lucas Moyano
     * */
    public SentenceNode(SemanticContext currentContext) {
        this.currentContext = currentContext;
    }
}
