package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class AllredyExist extends SemanticException {

    public AllredyExist(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return this.getToken().getLexeme() + " YA FUE DECLARADO";
    }
}
