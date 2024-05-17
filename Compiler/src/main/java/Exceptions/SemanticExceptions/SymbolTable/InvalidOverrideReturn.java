package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de sobreescribir un metodo y declarar
 * un tipo de return distinto
 * @author Yeumen Silva
 */
public class InvalidOverrideReturn extends SymbolTableException {

    public InvalidOverrideReturn(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "SOBRESCRITURA INCORRECTA: TIPO DE RET DISTINTO";
    }
}
