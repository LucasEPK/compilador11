package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateConstructor extends SemanticException{

    public DuplicateConstructor(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL CONSTRUCTOR YA ESTA DECLARADO EN ESTE ALCANCE";
    }
}