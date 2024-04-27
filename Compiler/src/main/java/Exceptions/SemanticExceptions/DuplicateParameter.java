package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateParameter extends SemanticException{

    public DuplicateParameter(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL PARAMETRO " + this.getToken().getLexeme() + " YA ESTÁ DECLARADO EN ESTE ALCANCE";
    }
}