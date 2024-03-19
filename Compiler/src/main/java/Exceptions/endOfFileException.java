package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de no haber EOF
 * @author Yeumen Silva
 */

public class endOfFileException extends LexicalException {


    public endOfFileException(Token token) {
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "FALLO EN EOF";
    }

}
