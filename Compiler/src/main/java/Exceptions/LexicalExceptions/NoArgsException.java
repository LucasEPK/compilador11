package Exceptions.LexicalExceptions;

/**
 * Error no se proporciono ningun argumento de entrada
 * @author Yeumen Silva
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
        return "ERROR, FALTA DE ARGUMENTOS: NO SE INGRESÓ NINGUN ARGUMENTO POR CONSOLA";
    }



}
