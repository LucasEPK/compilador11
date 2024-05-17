package Exceptions.SemanticExceptions.AST;

import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

/**
 * Clase que define error de acceder a atributo no definido
 * @author Yeumen Silva
 */

public class UndefinedReferenceAttribute extends ASTExcpetion {

    public UndefinedReferenceAttribute(Token token){
        super(token);
    }

    /**
     * Método que va a retornar un string con el mensaje de la excepción
     * @return String
     * @author Yeumen Silva
     */

    @Override
    public String getExceptionType() {
        return "EL ATRIBUTO AL QUE SE INTENTA ACCEDER NO ESTA DEFINIDO";
    }

}
