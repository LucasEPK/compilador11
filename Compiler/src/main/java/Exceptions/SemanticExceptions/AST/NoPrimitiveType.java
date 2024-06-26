package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

/**
 * Clase que define el error cuando el tipo no es primitivo
 * @autor Yeumen Silva
 */

public class NoPrimitiveType extends ASTExcpetion{


    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public NoPrimitiveType(Token token) {
        super(token);
    }

    /**
     * Método que retorna el mensaje de error
     * @return mensaje de error
     * @autor Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "EL TIPO NO ES PRIMITIVO";
    }
}
