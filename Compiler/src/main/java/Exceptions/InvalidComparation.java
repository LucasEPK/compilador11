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

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR EN LA COMPARACIÓN: OPERADOR DE COMPARACIÓN NO VALIDO";
    }
}
