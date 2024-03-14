package Exceptions;

/**
 * Clase que representa los errores de caracteres mal formados (ej: +, -, ++)
 * @author Yeumen Silva
 */
public class InvalidOperator extends LexicalException{

    public InvalidOperator(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA DEFINICION DEL OPERADOR: OPERADOR NO VALIDO";
    }
}
