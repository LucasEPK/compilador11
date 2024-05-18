package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que representa una excepción cuando se encuentra un return en el start
 * @autor Yeumen Silva
 */

public class ReturnInStart extends ASTExcpetion{
    /**
     * Constructor de nuestra clase que recibe un token con datos de lina, columna, lexema y token
     * @param token con datos de lina, columna, lexema y token
     * @autor Yeumen Silva
     */
    public ReturnInStart(Token token) {
        super(token);
    }

    /**
     * Método que retorna el tipo de excepción
     * @return string con el tipo de excepción
     * @autor Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "SRART NO PUEDE TENER RETURN";
    }
}
