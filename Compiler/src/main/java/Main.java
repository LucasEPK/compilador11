import Exceptions.NoArgsException;
import LexicalAnalyzer.Executor;

/**
 * Clase que recibe los argumentos de archivo de entrada y salida
 * @throws NoArgsException si no se recibe ningun argumento de entrada
 * @author Yeumen Silva
 */

public class Main {

    public static void main(String[] args) {
        String inputPath,outputPath;
        Executor executor = new Executor();

        /* Verifica si hay algun argumento de entrada, de otro modo
        lanza un error
         */

        try {
            if (args.length == 0) {
                throw new NoArgsException();
            }
            inputPath = args[0];
        } catch (NoArgsException e) {
            System.err.println(e.getExceptionType());
            return;
        }

        /* Dependiendo si la salida es por consola o no, llama a una función
        u otra de la clase Executor
         */

        if (args.length < 2){

            executor.startExecution(inputPath,null);

        }
        else    {

            outputPath = args[1];
            executor.startExecution(inputPath,outputPath);

        };




    }

}
