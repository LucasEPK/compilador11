package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que define el error cuando no se encuentra un struct en la tabla de simbolos
 */
public class StructNotFound extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public StructNotFound(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "NO SE ENCONTRO EL STRUCT EN LA TABLA DE SIMBOLOS";
    }
}
