package Exceptions.LexicalExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de que no se cerró el caracter (falta ')
 * @author Yeumen Silva
 */
public class NoClosedChar extends  LexicalException{

    public NoClosedChar(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL CARACTER: NO SE CERRO";
    }
}
