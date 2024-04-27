package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class InvalidOverrideLength extends  SemanticException{

    public InvalidOverrideLength(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "SOBRESCRITURA INCORRECTA: NUMERO DE PARAMETROS DISTINTO";
    }
}
