package Exceptions.SemanticExceptions.AST;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que define que se intenta acceder a pos fuera de rango del arreglo
 * @author Yeumen Silva
 */

public class ArrayOutOfRange extends SemanticException {

    public ArrayOutOfRange(Token token){
        super(token);
    }

    /**
     * Método que va a retornar un string con el mensaje de la excepción
     * @return String
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "INDICE FUERA DE RANGO";
    }
}
