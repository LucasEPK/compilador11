package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

public class DiferentParamType extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     * @param token con datos de lina, columna, lexema y token
     */
    public DiferentParamType(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LOS TIPOS DE LA LLAMADA Y LOS PARAMETROS DEL METODO SON DIFERENTES";
    }
}
