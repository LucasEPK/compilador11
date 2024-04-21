package Exceptions.LexicalExceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de simbolos no validos
 * @author Yeumen Silva
 */
public class SymbolException extends LexicalException{
    public SymbolException(Token token){
        super(token);
    }

    /**
     * Método que devuelve un mensaje con el error
     * @return String que contiene el mesnaje de error
     * @author Yeumen Silva
     */
    @Override
    public String getExceptionType() {
        return "ERROR SIMBOLO NO VALIDO: EL SIMBOLO DEFINIDO NO ES PARTE DEL ALFABETO";
    }
}
