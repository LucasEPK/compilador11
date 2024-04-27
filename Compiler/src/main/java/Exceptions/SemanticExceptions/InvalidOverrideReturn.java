package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class InvalidOverrideReturn extends  SemanticException{

    public InvalidOverrideReturn(Token token){
        super(token);
    }
    @Override
    public String getExceptionType() {
        return "SOBRESCRITURA INCORRECTA: TIPO DE RET DISTINTO";
    }
}
