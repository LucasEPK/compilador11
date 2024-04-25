package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateStruct extends  SemanticException{

    public DuplicateStruct(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "YA EXISTE STRUCT DE ESTA CLASE";
    }
}
