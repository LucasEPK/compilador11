package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class InvalidOverride extends  SemanticException{

    public InvalidOverride(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "SE SOBRESCRIBIÓ EL MÉTODO INCORRECTAMENTE";
    }
}
