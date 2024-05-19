package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que representa una excepción cuando la cantidad de argumentos de una llamada
 * es diferente a la cantidad de parametros de la función
 */

public class LengthOfCallInvalid extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public LengthOfCallInvalid(Token token) {
        super(token);
    }

    /**
     * Método que retorna el mensaje de error
     * @return mensaje de error
     */

    @Override
    public String getExceptionType() {
        return "LA CANTIDAD DE ARGUMENTOS DE LA LLAMADA ES DIFERENTE A LA CANTIDAD DE PARAMETROS DE LA FUNCION";
    }
}
