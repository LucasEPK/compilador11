package Exceptions;

/**
 * Clase que representa los errores de simbolos no validos
 * @author Yeumen Silva
 */
public class SymbolException extends LexicalException{
    public SymbolException(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR SIMBOLO NO VALIDO: EL SIMBOLO DEFINIDO NO ES PARTE DEL ALFABETO";
    }
}
