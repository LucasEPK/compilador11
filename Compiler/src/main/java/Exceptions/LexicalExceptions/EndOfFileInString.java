package Exceptions.LexicalExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de EOF dentro de String
 * @author Yeumen Silva
 */

public class EndOfFileInString  extends  LexicalException{

    public EndOfFileInString(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR EOF DENTRO DE STRING";
    }



}
