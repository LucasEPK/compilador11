package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que define el error cuando se intenta acceder a una variable privada heredada

 */
public class PrivateVar extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public PrivateVar(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "SE ESTA INTENTANDO ACCEDER A UNA VARIABLE PRIVADA";
    }
}
