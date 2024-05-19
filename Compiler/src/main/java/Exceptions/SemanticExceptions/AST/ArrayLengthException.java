package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que define el error cuando la posición de acceso a un arreglo no es un entero
 * @autor Yeumen Silva
 */

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
