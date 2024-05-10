package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;


/**
 * Clase que representa que no se declaro un constructor
 * @author Yeumen Silva
 */
public class UndefinedConstructor extends SemanticException {

    public UndefinedConstructor(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "CONSTRUCTOR NO DECLARADO";
    }
}
