package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de sobreescribir un método y enviar un
 * parametro de distinto tipo
 * @author Yeumen Silva
 */
public class InvalidOverrideType extends SemanticException{

    public  InvalidOverrideType(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "SOBRESCRITURA INCORRECTA: TIPO DE PARAMETRO DISTINTO";
    }
}
