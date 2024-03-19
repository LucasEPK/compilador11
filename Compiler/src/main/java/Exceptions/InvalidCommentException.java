package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de comentario invalido
 * @author Yeumen Silva
 */

public class InvalidCommentException extends LexicalException {

    public InvalidCommentException(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL COMENTARIO";
    }


}
