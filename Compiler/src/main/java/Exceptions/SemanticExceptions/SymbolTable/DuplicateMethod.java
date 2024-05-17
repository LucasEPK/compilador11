package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de declarar dos metodos
 * con igual nombre para una misma struct
 * @author Lucas Moyano
 */
public class DuplicateMethod extends SymbolTableException {

    public DuplicateMethod(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Lucas Moyano
     */

    @Override
    public String getExceptionType() {
        return "EL MÉTODO YA ESTA DECLARADO";
    }
}
