package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class AttributteOrMethodHeritance extends SemanticException{
    public AttributteOrMethodHeritance(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "YA SE ENCUENTRA DECLARADO EN UN ANCESTRO";
    }
}
