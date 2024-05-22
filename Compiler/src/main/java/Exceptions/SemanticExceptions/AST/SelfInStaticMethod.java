package Exceptions.SemanticExceptions.AST;

import LexicalAnalyzer.Token;

public class SelfInStaticMethod extends ASTExcpetion{
    /**
     * Constructor de nuestra clase
     *
     * @param token con datos de lina, columna, lexema y token
     */
    public SelfInStaticMethod(Token token) {
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "NO ES POSIBLE USAR SELF EN UN METODO ESTATICO";
    }
}
