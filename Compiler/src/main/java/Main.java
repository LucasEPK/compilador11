import Exceptions.LexicalExceptions.NoArgsException;
import LexicalAnalyzer.Executor;
import SyntacticAnalyzer.SyntacticExecutor;

/**
 * Clase que recibe los argumentos de archivo de entrada y salida
 * @throws NoArgsException si no se recibe ningun argumento de entrada
 * @author Yeumen Silva
 */

public class Main {

    public static void main(String[] args) {
        String inputPath;
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


        newExecutor(args,inputPath);





    }

    /**
     * Método que llama al antiguo Executor
     * @author Yeumen Silva
     * */
    private static void oldExecutor(String[] args, String inputPath){

        /* Dependiendo si la salida es por consola o no, llama a una función
        u otra de la clase Executor
         */

        Executor executor = new Executor();
        if (args.length < 2){

            executor.startExecution(inputPath,null);

        }
        else    {

            executor.startExecution(inputPath,args[1]);

        };

    }

    /**
     * Método que llama al nuevo Executor
     * @author Yeumen Silva
     * */

    private static void newExecutor(String[] args, String inputPath ){
        SyntacticExecutor syntacticExecutor;
        if (args.length < 2){

            syntacticExecutor = new SyntacticExecutor(inputPath,null);

        }
        else    {

            syntacticExecutor = new SyntacticExecutor(inputPath,args[1]);

        };

    }
}
