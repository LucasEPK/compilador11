package Exceptions;

/**
 * Clase que representa los errores de strings mal formados
 * @author Yeumen Silva
 */
public class StringException extends LexicalException {

    public StringException(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL STRING: STRING NO VALIDO";
    }
}
