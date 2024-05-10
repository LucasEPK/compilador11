package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de declarar
 * dos variables con mismo id para una misma struct
 * @author Lucas Moyano
 */

public class DuplicateVariable extends SemanticException {

    public DuplicateVariable(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Lucas Moyano
     */

    @Override
    public String getExceptionType() {
        return "LA VARIABLE " + this.getToken().getLexeme() + " YA ESTÁ DECLARADA";
    }
}