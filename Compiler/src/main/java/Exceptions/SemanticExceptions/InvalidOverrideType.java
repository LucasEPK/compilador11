package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class InvalidOverrideType extends SemanticException{

    public  InvalidOverrideType(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "SOBRESCRITURA INCORRECTA: TIPO DE PARAMETRO DISTINTO";
    }
}
