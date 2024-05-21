package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que maneja la excepción de un método en start
 */

public class MethodInStart extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public MethodInStart(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LLAMADA A MÉTODO DESDE START (START NO PUEDE TENER MÉTODOS)";
    }
}
