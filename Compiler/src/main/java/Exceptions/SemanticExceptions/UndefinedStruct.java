package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class UndefinedStruct extends  SemanticException{

    public UndefinedStruct(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL STRUCT NO ESTA DEFINIDO";
    }
}
