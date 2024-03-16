package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de enteros mal formados
 * @author Yeumen Silva
 */
public class IntException extends  LexicalException{
    public IntException(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL ENTERO: ENTERO NO VALIDO";
    }
}
