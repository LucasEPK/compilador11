package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateVariable extends SemanticException{

    public DuplicateVariable(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LA VARIABLE " + this.getToken().getLexeme() + " YA ESTÁ DECLARADA";
    }
}