package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de atributos ya declarados en una superclase
 * @author Yeumen Silva
 */

public class DuplicateAttributeHeritance  extends SymbolTableException {

    public DuplicateAttributeHeritance(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "EL ATIBUTO YA ESTA DECLARADO EN UNA SUPERCLASE";
    }
}
