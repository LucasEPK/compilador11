package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de simbolos no validos
 * @author Yeumen Silva
 */
public class SymbolException extends LexicalException{
    public SymbolException(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "ERROR SIMBOLO NO VALIDO: EL SIMBOLO DEFINIDO NO ES PARTE DEL ALFABETO";
    }
}
