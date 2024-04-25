package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateImpl extends SemanticException{

    public DuplicateImpl(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "YA EXISTE IMPL DE ESTA CLASE";
    }
}
