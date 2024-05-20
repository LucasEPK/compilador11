package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que representa una excepción cuando se intenta usar self en el método start
 */

public class SelfInStart extends ASTExcpetion{

    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public SelfInStart(Token token) {
        super(token);
    }

    /**
     * Método que retorna el mensaje de error
     * @return mensaje de error
     */

    @Override
    public String getExceptionType() {
        return "NO ES POSIBLE USAR SELF EN START";
    }
}
