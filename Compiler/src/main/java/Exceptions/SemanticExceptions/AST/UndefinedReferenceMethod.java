package Exceptions.SemanticExceptions.AST;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que define error cuando se intenta acceder
 * a método no definido
 * @author Yeumen Silva
 */

public class UndefinedReferenceMethod extends ASTExcpetion {

    public UndefinedReferenceMethod(Token token){
        super(token);
    }

    /**
     * Método que va a retornar un string con el mensaje de la excepción
     * @return String
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "EL METODO AL QUE SE INTENTA ACCEDER NO ESTA DEFINIDO";
    }
}
