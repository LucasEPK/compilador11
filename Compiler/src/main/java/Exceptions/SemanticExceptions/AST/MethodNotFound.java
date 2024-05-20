package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que define el error cuando no se encuentra un método en la tabla de simbolos
 */
public class MethodNotFound extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public MethodNotFound(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "NO SE ENCONTRO EL MÉTODO EN LA TABLA DE SIMBOLOS";
    }
}
