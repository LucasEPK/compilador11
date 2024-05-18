package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que se lanza cuando un método tiene múltiples retornos diferentes
 */
public class MultipleReturnType extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     * @autor Yeumen Silva
     */

    public MultipleReturnType(Token token) {
        super(token);
    }

    /**
     * Método que retorna el mensaje de error
     * @return mensaje de error
     * @autor Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "MULTIPLES RETORNOS DE UN MÉTODO DIFERENTES";
    }
}
