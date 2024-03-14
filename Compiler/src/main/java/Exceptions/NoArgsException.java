package Exceptions;

/**
 * Error no se proporciono ningun argumento de entrada
 */
public class NoArgsException extends RuntimeException {

    public NoArgsException(){
        super();
    }

    /**
     * Método que lanza el error de mi clase
     * @author Yeumen Silva
     */
    public String getExceptionType(){
        return "NO ARGS EXCEPTION: No se ingresó ningun argumento por consola";
    }



}
