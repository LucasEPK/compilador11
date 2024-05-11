package SemanticAnalyzer.AST;
import SemanticAnalyzer.SymbolTable.Struct;
import SemanticAnalyzer.SymbolTable.Methods;


/**
 * Clase que guarda el currentStruct y el currentMethod que se tiene
 * en ese momento en la tabla de simbolos
 * @author Lucas Moyano
 * */
public class SemanticContext {

    private Struct currentStruct;
    private Methods currentMethod;

    /**
     * @param currentStruct currentStruct de la tabla de simbolos en este momento
     * @param currentMethod currentMethod de la tabla de simbolos en este momento
     * @author Lucas Moyano
     * */
    public SemanticContext(Struct currentStruct, Methods currentMethod) {
        this.currentStruct = currentStruct;
        this.currentMethod = currentMethod;
    }

    public Struct getCurrentStruct() {
        return currentStruct;
    }

    public Methods getCurrentMethod() {
        return currentMethod;
    }
}
