package Exceptions;

/**
 * Clase que representa los errores de caracteres mal formados
 * @author Yeumen Silva
 */
public class CharException extends LexicalException{

    public CharException(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL CARACTER: CARACTER NO VALIDO";
    }
}
