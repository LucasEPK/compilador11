package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class InvalidHeritance extends SemanticException{

    public InvalidHeritance(Token token){
        super(token);

    }

    @Override
    public String getExceptionType() {
        return "NO ES POSIBLE HEREDAR DE ESA CLASE";
    }
}
