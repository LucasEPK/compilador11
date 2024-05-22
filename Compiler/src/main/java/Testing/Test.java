package Testing;
import java.io.File;

import Exceptions.LexicalExceptions.LexicalException;
import LexicalAnalyzer.Executor;
import SyntacticAnalyzer.SyntacticExecutor;


/**
 * Clase que es la encargada de ejecutar los tests de cada etapa
 * @author Yeumen Silva
 */

public class Test {




    /**
     * Método que es ejecuta los tests del analizador sintáctico
     * @author Yeumen Silva
     */

    public void executorTest(){
        folderExecutor("src/main/java/Testing/Tests/SemanticTests/AST/passing", "Pass");
        folderExecutor("src/main/java/Testing/Tests/SemanticTests/AST/failing", "Fail");
    }



    private void folderExecutor(String path, String type) {

        SyntacticExecutor syntacticExecutor;
        // Guardo todos los archivos de la carpeta
        File[] files = readFolder(path);

        if (type.equals("Pass")) {
            System.out.println("ARCHIVOS QUE DEBERÍAN PASAR EL SEMÁNTICO\n");
        } else {
            System.out.println("ARCHIVOS QUE NO DEBERÍAN PASAR EL SEMÁNTICO\n");
        }

        for (File file : files) {
            // Solo procesa archivos con extensión .ru
            if (file.getName().endsWith(".ru")) {
                // Imprimo el nombre del archivo para identificarlo
                System.out.println("Resultado de prueba: " + file.getName());

                // Ejecuto con executor como se haría desde el Sintáctico
                syntacticExecutor = new SyntacticExecutor(file.getAbsolutePath(), null);
                System.out.println("\n");
            }
        }
    }


    /**
     * Método que dado un path de una carpeta, lee todos los
     * archivos, los alamcena en una lista y luego ejecuta los tests
     * @author Yeumen Silva
     */

    private File[] readFolder(String path){

        File folder = new File(path);

        return  folder.listFiles();
    }



    // Main que invoca al tester léxico
    public static void main(String[] args) {

        Test tester = new Test();

        tester.executorTest();


    }

}
