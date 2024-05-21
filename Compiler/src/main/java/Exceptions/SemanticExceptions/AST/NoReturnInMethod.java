package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que se lanza cuando un método no tiene un return
 * @autor Yeumen Silva
 */

public class NoReturnInMethod extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public NoReturnInMethod(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL METODO NO TIENE RET";
    }
}
