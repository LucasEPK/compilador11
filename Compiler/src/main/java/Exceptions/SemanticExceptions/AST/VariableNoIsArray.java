package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que define el error cuando se intenta acceder a una variable
 * como si fuera un arreglo
 */
public class VariableNoIsArray extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     *
     * @param token con datos de lina, columna, lexema y token
     */
    public VariableNoIsArray(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LA VARIABLE NO ES UN ARREGLO";
    }
}
