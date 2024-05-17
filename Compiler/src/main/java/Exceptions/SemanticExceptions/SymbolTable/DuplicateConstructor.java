package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de declarar dos constructores
 * @author Lucas Moyano
 */

public class DuplicateConstructor extends SymbolTableException {

    public DuplicateConstructor(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Lucas Moyano
     */

    @Override
    public String getExceptionType() {
        return "EL CONSTRUCTOR YA ESTA DECLARADO EN ESTE ALCANCE";
    }
}