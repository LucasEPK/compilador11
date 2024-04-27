package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa una herencia ciclica
 * @author Yeumen Silva
 */
public class HeritanceCycle extends SemanticException {

    public HeritanceCycle(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "SE PRODUJO UN CICLO EN LA HERENCIA";
    }
}
