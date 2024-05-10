package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;


/**
 * Clase que representa los errores de sobreescribir un metodo y declarar
 * un metodo estatico y el otro no
 * @author Yeumen Silva
 */
public class InvalidOverrideStatic extends SemanticException{

    public InvalidOverrideStatic(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "SOBRESCRITURA INCORRECTA: NO SE PUEDE REDEFINIR UN METODO ESTATICO";
    }
}
