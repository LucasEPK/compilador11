package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de declarar dos atributos con mismo id
 * @author Lucas Moyano
 */

public class DuplicateAttribute extends SemanticException {

    public DuplicateAttribute(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Lucas Moyano
     */
    @Override
    public String getExceptionType() {
        return "EL ATRIBUTO " + getToken().getLexeme() + " YA ESTA DECLARADO EN EL ALCANCE";
    }
}
