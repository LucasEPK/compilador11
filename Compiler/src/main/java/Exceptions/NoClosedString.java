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

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DE LA CADENA: NO SE CERRO";
    }
}
