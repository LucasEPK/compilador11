package Exceptions;

/**
 * Clase que representa los errores de id mal formados
 * @author Yeumen Silva
 */
public class InvalidId  extends LexicalException{

    public InvalidId(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL IDENTIFICADOR: IDENTIFICADOR NO VALIDO";
    }
}
