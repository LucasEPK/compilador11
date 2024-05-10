package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de heredar de una clase primitiva
 * @author Yeumen Silva
 */
public class InvalidHeritance extends SemanticException {

    public InvalidHeritance(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "NO ES POSIBLE HEREDAR DE ESA CLASE";
    }
}
