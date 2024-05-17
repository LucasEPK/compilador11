package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de declarar
 * una variable con mismo nombre que parametro de método
 * @author Yeumen Silva
 */
public class InvalidVariableName extends SymbolTableException {

    public InvalidVariableName(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "EL IDENTIFICADOR YA ESTA DECLARADO EN LOS PARAMETROS";
    }
}
