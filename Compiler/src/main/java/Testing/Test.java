package Testing;
import java.io.File;
import Exceptions.NoArgsException;
import LexicalAnalyzer.Executor;


/**
 * Clase que es la encargada de ejecutar los tests de cada etapa
 * @author Yeumen Silva
 */

public class Test {


    /**
     * Método que es ejecuta los tests del analizador lexico
     * @throws Exceptions.LexicalException
     * @author Yeumen Silva
     */
    public void lexicalTest(){
        Executor executor = new Executor();

        //guardo todos los archivos de la carpeta
        File[] files = readFolder("Compiler/src/main/java/Testing/Tests/LexicalTests");

        for (File file : files) {

            //Imprimo el nombre del archivo para identificarlo
            System.out.println("Resultado de prueba: " + file.getName());

            //Ejecuto con executor como se haria desde el main
            executor.startExecution(file.getAbsolutePath());
            System.out.println("\n");
        }
    }

    /**
     * Método que dado un path de una carpeta, lee todos los
     * archivos y los alamcena en una lista
     * @author Yeumen Silva
     */

    private File[] readFolder(String path){

        File folder = new File(path);

        return  folder.listFiles();
    }

    // Main que invoca al tester léxico
    public static void main(String[] args) {

        Test tester = new Test();

        tester.lexicalTest();

    }

}
