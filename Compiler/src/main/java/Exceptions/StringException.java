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

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL STRING: STRING NO VALIDO";
    }
}
