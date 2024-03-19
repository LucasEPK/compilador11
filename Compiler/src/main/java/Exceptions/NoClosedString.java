package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de que no se cerro el string (faltan ")
 * @author Yeumen Silva
 */
public class NoClosedString extends LexicalException{
    public NoClosedString(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DE LA CADENA: NO SE CERRO";
    }
}
