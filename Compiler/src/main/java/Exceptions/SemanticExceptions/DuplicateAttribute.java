package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateAttribute extends SemanticException{

    public DuplicateAttribute(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL ATRIBUTO YA ESTÁ DECLARADO";
    }
}
