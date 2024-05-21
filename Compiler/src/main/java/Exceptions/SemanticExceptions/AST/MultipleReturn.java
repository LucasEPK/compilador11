package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepcion que se lanza cuando no se encuentra una variable
 */

public class MultipleReturn extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public MultipleReturn(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "HAY MAS DE UN RETURN Y DEVUELVEN TIPOS DIFERENTES";
    }
}
