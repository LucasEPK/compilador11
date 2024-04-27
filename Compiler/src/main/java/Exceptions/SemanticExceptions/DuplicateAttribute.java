package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateAttribute extends SemanticException{

    public DuplicateAttribute(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL ATRIBUTO " + getToken().getLexeme() + " YA ESTA DECLARADO EN EL ALCANCE";
    }
}
