package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa que no se declaro impl para un struct
 * @author Yeumen Silva
 */
public class UndefinedImpl extends SemanticException{

    public UndefinedImpl(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "EL IMPL NO ESTA DEFINIDO";
    }
}
