package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateMethod extends SemanticException{

    public DuplicateMethod(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL MÉTODO YA ESTA DECLARADO";
    }
}
