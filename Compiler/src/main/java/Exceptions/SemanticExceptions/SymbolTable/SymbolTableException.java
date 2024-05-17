package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

public abstract class SymbolTableException  extends SemanticException {

    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public SymbolTableException(Token token) {
        super(token);
    }
}
