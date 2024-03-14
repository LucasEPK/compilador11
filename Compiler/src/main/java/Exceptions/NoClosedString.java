package Exceptions;

/**
 * Clase que representa el error de que no se cerro el string (faltan ")
 * @author Yeumen Silva
 */
public class NoClosedString extends LexicalException{
    public NoClosedString(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DE LA CADENA: NO SE CERRÓ";
    }
}
