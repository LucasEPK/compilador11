package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que se lanza cuando se intenta comparar dos tipos que no son booleanos
 */

public class InvalidLogicalComparation extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public InvalidLogicalComparation(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LOS TIPOS DE LA COMAPRACION NO SON BOOLEANOS";
    }
}
