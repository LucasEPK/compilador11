package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que se lanza cuando se intenta asignar un valor nil a un tipo primitivo
 */
public class InvalidNilPrimitive extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public InvalidNilPrimitive(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "NO ES POSIBLE ASIGNAR NIL A UN TIPO PRIMITIVO";
    }
}
