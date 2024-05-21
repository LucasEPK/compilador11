package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que maneja la excepción de una asignación a void
 */
public class VoidAsignation extends ASTExcpetion{

    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public VoidAsignation(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "NO ES POSIBLE ASIGNAR VOID A UNA VARIABLE";
    }
}
