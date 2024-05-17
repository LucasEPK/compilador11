package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de sobrescribir mal un método
 * teniendo una longitud de parametros distinta
 * @author Yeumen Silva
 */

public class InvalidOverrideLength extends SymbolTableException {

    public InvalidOverrideLength(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "SOBRESCRITURA INCORRECTA: NUMERO DE PARAMETROS DISTINTO";
    }
}
