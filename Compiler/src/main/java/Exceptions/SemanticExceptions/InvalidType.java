package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de declarar
 * una variable de un tipo no definido
 * @author Yeumen Silva
 */
public class InvalidType extends SemanticException{
    public InvalidType( Token token) {
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "EL TIPO DECLARADO NO EXISTE";
    }
}
