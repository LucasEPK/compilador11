package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Excepción que indica que la condición de un while o if no es booleana
 */
public class ConditionNoIsBool extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     *
     * @param token con datos de lina, columna, lexema y token
     */
    public ConditionNoIsBool(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LA CONDICION DE LA SENTENCIA NO ES UN BOOL";
    }
}
