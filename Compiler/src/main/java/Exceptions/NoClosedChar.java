package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa el error de que no se cerró el caracter (falta ')
 * @author Yeumen Silva
 */
public class NoClosedChar extends  LexicalException{

    public NoClosedChar(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL CARACTER: NO SE CERRO";
    }
}
