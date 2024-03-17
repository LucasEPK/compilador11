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
    @Override
    public String getExceptionType() {
        return "FALLO EN EOF";
    }

}
