package Exceptions;

/**
 * Clase que representa los errores de enteros mal formados
 * @author Yeumen Silva
 */
public class IntException extends  LexicalException{
    public IntException(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL ENTERO: ENTERO NO VALIDO";
    }
}
