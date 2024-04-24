package Exceptions.SemanticExceptions;

import LexicalAnalyzer.Token;

public class HeritanceCycle extends SemanticException {

    public HeritanceCycle(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "SE PRODUJO UN CICLO EN LA HERENCIA";
    }
}
