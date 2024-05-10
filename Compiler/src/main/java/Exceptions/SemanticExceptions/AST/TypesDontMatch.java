package Exceptions.SemanticExceptions.AST;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que define que los tipos de asignación no matchean
 * @author Yeumen Silva
 */

public class TypesDontMatch extends SemanticException {

    public TypesDontMatch(Token token){
        super(token);
    }

    /**
     * Método que va a retornar un string con el mensaje de la excepción
     * @return String
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "TIPOS DISTINTOS";
    }
}
