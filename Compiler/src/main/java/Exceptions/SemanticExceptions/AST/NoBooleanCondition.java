package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que se lanza cuando una condición no es booleana
 */

public class NoBooleanCondition extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     * @autor Yeumen Silva
     */
    public NoBooleanCondition(Token token) {
        super(token);
    }

    /**
     * Método que retorna el mensaje de error
     * @return mensaje de error
     * @autor Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "LA CONDICIÓN NO ES BOOLEANA";
    }
}
