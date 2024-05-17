package Exceptions.SemanticExceptions.AST;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

public abstract class ASTExcpetion extends SemanticException {

    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public ASTExcpetion(Token token) {
        super(token);
    }
}
