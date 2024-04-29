package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class UndefinedConstructor extends  SemanticException{

    public UndefinedConstructor(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "CONSTRUCTOR NO DECLARADO";
    }
}
