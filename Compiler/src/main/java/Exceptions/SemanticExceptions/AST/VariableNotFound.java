package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepcion que se lanza cuando no se encuentra una variable
 * tanto en el metodo, parametro o en la clase

 */
public class VariableNotFound extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     *
     * @param token con datos de lina, columna, lexema y token
     */
    public VariableNotFound(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "VARIABLE NO DEFINIDA";
    }
}
