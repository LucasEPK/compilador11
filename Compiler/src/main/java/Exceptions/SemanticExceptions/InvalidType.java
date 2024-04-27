package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class InvalidType extends SemanticException{
    public InvalidType( Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL TIPO NO EXISTE";
    }
}
