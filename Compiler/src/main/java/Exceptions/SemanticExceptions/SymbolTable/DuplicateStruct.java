package Exceptions.SemanticExceptions.SymbolTable;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de declarar dos struct para una misma struct
 * @author Yeumen Silva
 */
public class DuplicateStruct extends SemanticException {

    public DuplicateStruct(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "YA EXISTE STRUCT DE ESTA CLASE";
    }
}
