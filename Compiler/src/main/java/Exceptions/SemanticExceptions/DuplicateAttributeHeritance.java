package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class DuplicateAttributeHeritance  extends  SemanticException{

    public DuplicateAttributeHeritance(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "EL ATIBUTO YA ESTA DECLARADO EN UNA SUPER CLASE";
    }
}
