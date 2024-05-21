package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que maneja la excepción de un return en un constructor
 */
public class ReturnInConstructor extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     *
     * @param token con datos de lina, columna, lexema y token
     */
    public ReturnInConstructor(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL CONSTRUCTOR NO PUEDE TENER RETURN";
    }
}

