package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que se lanza cuando se intenta llamar a un método no estático
 * como si fuera estático
 */
public class NotEstaticMethod extends ASTExcpetion{

    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public NotEstaticMethod(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL MÉTODO NO ES ESTÁTICO";
    }
}
