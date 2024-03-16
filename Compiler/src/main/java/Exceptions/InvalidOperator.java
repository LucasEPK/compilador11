package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de caracteres mal formados (ej: +, -, ++)
 * @author Yeumen Silva
 */
public class InvalidOperator extends LexicalException{

    public InvalidOperator(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL OPERADOR: OPERADOR NO VALIDO";
    }
}
