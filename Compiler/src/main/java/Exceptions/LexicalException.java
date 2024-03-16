package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase abstracta que define los tipos de errores lexicos
 * de nuestro compilador
 * @author Yeumen Silva
 */

public abstract class LexicalException extends RuntimeException {

    private Token token;

    /**
     * Constructor de clase LexicalException
     * @param token es una instancia de la clase Token con la
     * informaciòn del error
     * @author Yeumen Silva
     */
    public LexicalException(Token token){
        super();
        this.token = token;
    }

    /**
     * Método que va a lanzar el mensa¡e de error dependiendo del tipo de error
     * @author Yeumen Silva
     */

    public abstract String getExceptionType();

    public Token getToken(){
        return this.token;
    }



}
