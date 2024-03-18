package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de comentario invalido
 * @author Yeumen Silva
 */

public class invalidCommentException extends LexicalException {

    public invalidCommentException(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL COMENTARIO";
    }


}
