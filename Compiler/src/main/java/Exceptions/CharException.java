package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de caracteres mal formados
 * @author Yeumen Silva
 */
public class CharException extends LexicalException{

    public CharException(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL CARACTER: CARACTER NO VALIDO";
    }
}
