package Exceptions;

/**
 * Clase abstracta que define los tipos de errores lexicos
 * de nuestro compilador
 * @author Yeumen Silva
 */

public abstract class LexicalException extends RuntimeException {

    private int line, column;

    private String lexeme;

    /**
     * Constructor de clase LexicalException
     * @param line número de linea del error
     * @param column número de columna del error
     * @param lexeme lexema que genera el error
     * @author Yeumen Silva
     */
    public LexicalException(int line, int column, String lexeme){
        super();
        this.line = line;
        this.column = column;
        this.lexeme = lexeme;
    }

    /**
     * Método que va a lanzar el mensa¡e de error dependiendo del tipo de error
     * @author Yeumen Silva
     */

    public abstract String getExceptionType();



}
