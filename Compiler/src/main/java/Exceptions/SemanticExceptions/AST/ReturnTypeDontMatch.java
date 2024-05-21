package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepcion que se lanza cuando los ret no coinciden
 */
public class ReturnTypeDontMatch extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public ReturnTypeDontMatch(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LOS TIPOS DE RETORNO NO COINCIDEN";
    }
}
