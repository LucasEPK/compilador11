package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que representa una excepción cuando se intenta realizar una operación
 * con dos operandos que no sean de tipo int
 */

public class NeedToBeInt extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     *
     * @param token con datos de lina, columna, lexema y token
     */
    public NeedToBeInt(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "PARA REALIZAR ESTA OPERACIÓN AMBOS OPERANDOS DEBEN SER DE TIPO INT";
    }
}
