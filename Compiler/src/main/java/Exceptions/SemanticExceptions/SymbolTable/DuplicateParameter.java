package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;
/**
 * Clase que representa los errores de declarar dos parámetros
 * con mismo id
 * @author Lucas Moyano
 */
public class DuplicateParameter extends SymbolTableException {

    public DuplicateParameter(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Lucas Moyano
     */

    @Override
    public String getExceptionType() {
        return "EL PARAMETRO " + this.getToken().getLexeme() + " YA ESTÁ DECLARADO EN ESTE ALCANCE";
    }
}