package Exceptions.LexicalExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de id mal formados
 * @author Yeumen Silva
 */
public class InvalidId  extends LexicalException{

    public InvalidId(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL IDENTIFICADOR: IDENTIFICADOR NO VALIDO";
    }
}
