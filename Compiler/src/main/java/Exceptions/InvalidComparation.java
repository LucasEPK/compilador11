package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de comparación: el operador de comparación
 * no es valido
 * @author Yeumen Silva
 */
public class InvalidComparation extends LexicalException{

    public InvalidComparation(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA COMPARACIÓN: OPERADOR DE COMPARACIÓN NO VALIDO";
    }
}
