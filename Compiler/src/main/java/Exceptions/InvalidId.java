package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de id mal formados
 * @author Yeumen Silva
 */
public class InvalidId  extends LexicalException{

    public InvalidId(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL IDENTIFICADOR: IDENTIFICADOR NO VALIDO";
    }
}
