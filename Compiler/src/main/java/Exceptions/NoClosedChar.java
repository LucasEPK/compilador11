package Exceptions;
/**
 * Clase que representa el error de que no se cerró el caracter (falta ')
 * @author Yeumen Silva
 */
public class NoClosedChar extends  LexicalException{

    public NoClosedChar(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL CARACTER: NO SE CERRÓ";
    }
}
