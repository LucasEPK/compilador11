package Testing;
import java.io.File;
import java.util.Objects;

import Exceptions.NoArgsException;
import LexicalAnalyzer.Executor;
import SyntacticAnalyzer.SyntacticAnalyzer;
import SyntacticAnalyzer.SyntacticExecutor;


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
        folderExecutorLexical("src/main/java/Testing/Tests/LexicalTests/passing", "Pass");
        folderExecutorLexical("src/main/java/Testing/Tests/LexicalTests/failing", "Fail");
    }

    /**
     * Método que es ejecuta los tests del analizador sintáctico
     * @throws Exceptions.LexicalException
     * @author Yeumen Silva
     */

    public void SyntacticTest(){
        folderExecutorSyntactic("src/main/java/Testing/Tests/LexicalTests/passing", "Pass");
        folderExecutorSyntactic("src/main/java/Testing/Tests/LexicalTests/failing", "Fail");
    }


    private void folderExecutorLexical(String path, String type){

        Executor executor = new Executor();

        //guardo todos los archivos de la carpeta
        File[] files = readFolder(path);

        if (type.equals("Pass")) {
            System.out.println("ARCHIVOS QUE PASAN EL LEXICO\n");
        }else {
            System.out.println("ARCHIVOS QUE NO PASAN EL LEXICO\n");
        }

        for (File file : files) {

            //Imprimo el nombre del archivo para identificarlo
            System.out.println("Resultado de prueba: " + file.getName());

            //Ejecuto con executor como se haria desde el main
            executor.startExecution(file.getAbsolutePath(),null);
            System.out.println("\n");
        }

    }

    private void folderExecutorSyntactic(String path, String type){

        SyntacticExecutor syntacticExecutor;
        //guardo todos los archivos de la carpeta
        File[] files = readFolder(path);

        if (type.equals("Pass")) {
            System.out.println("ARCHIVOS QUE PASAN EL SINTÁCTICO\n");
        }else {
            System.out.println("ARCHIVOS QUE NO PASAN EL SINTÁCTICO\n");
        }

        for (File file : files) {

            //Imprimo el nombre del archivo para identificarlo
            System.out.println("Resultado de prueba: " + file.getName());

            //Ejecuto con executor como se haria desde el Sintáctico
            syntacticExecutor = new SyntacticExecutor(path,null);
            System.out.println("\n");
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

        tester.SyntacticTest();

        //tester.lexicalTest();

    }

}
