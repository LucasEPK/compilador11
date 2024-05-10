package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa que no se declaro el Struct
 * @author Yeumen Silva
 */

public class UndefinedStruct extends SemanticException {

    public UndefinedStruct(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "EL STRUCT NO ESTA DEFINIDO";
    }
}
