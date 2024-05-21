package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que define el error cuando la cantidad de parametros es distinta
 */
public class InvalidParamSize extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public InvalidParamSize(Token token) {
        super(token);
    }

    /**
     * Método que retorna el tipo de excepción
     * @return tipo de excepción
     */
    @Override
    public String getExceptionType() {
        return "LA CANTIDAD DE PARAMETROS ES DISTINTA";
    }
}
