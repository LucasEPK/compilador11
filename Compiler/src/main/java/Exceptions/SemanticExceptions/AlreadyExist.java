package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class AlreadyExist extends SemanticException {

    public AlreadyExist(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return this.getToken().getLexeme() + " YA FUE DECLARADO";
    }
}
