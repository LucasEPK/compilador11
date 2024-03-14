package Exceptions;

/**
 * Clase que representa los errores de comparación: el operador de comparación
 * no es valido
 * @author Yeumen Silva
 */
public class InvalidComparation extends LexicalException{

    public InvalidComparation(int line, int column, String lexeme){
        super(line,column,lexeme);
    }

    @Override
    public String getExceptionType() {
        return "ERROR EN LA COMPARACIÓN: OPERADOR DE COMPARACIÓN NO VALIDO";
    }
}
