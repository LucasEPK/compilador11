package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class UndefinedImpl extends SemanticException{

    public UndefinedImpl(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL IMPL NO ESTA DEFINIDO";
    }
}
