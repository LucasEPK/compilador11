package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de strings mal formados
 * @author Yeumen Silva
 */
public class StringException extends LexicalException {

    public StringException(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL STRING: STRING NO VALIDO";
    }
}
