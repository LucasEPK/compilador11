package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

public class ArrayLengthException extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public ArrayLengthException(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LA POSICION DE ACCESO NO ES UN ENTERO";
    }
}
