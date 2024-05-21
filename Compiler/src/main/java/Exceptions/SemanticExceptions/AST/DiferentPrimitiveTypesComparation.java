package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que se lanza cuando se intenta comparar dos tipos primitivos diferentes
 */
public class DiferentPrimitiveTypesComparation extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public DiferentPrimitiveTypesComparation(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "NO SE PUEDEN COMPARAR TIPOS PRIMITIVOS DIFERENTES";
    }
}
