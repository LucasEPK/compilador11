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
     * Método que es ejecuta los tests del analizador lexico
     * @throws LexicalException
     * @author Yeumen Silva
     */
    public void lexicalTest(){
        folderExecutorLexical("src/main/java/Testing/Tests/LexicalTests/passing", "Pass");
        folderExecutorLexical("src/main/java/Testing/Tests/LexicalTests/failing", "Fail");
    }

    /**
     * Método que es ejecuta los tests del analizador sintáctico
     * @throws LexicalException
     * @author Yeumen Silva
     */

    public void SyntacticTest(){
        folderExecutorSyntactic("src/main/java/Testing/Tests/SyntacticTests/passing", "Pass");
        folderExecutorSyntactic("src/main/java/Testing/Tests/SyntacticTests/failing", "Fail");
    }


    private void folderExecutorLexical(String path, String type){

        Executor executor = new Executor();

        //guardo todos los archivos de la carpeta
        File[] files = readFolder(path);

        if (type.equals("Pass")) {
            System.out.println("ARCHIVOS QUE DEBERÍAN PASAR EL LEXICO\n");
        }else {
            System.out.println("ARCHIVOS QUE NO DEBERÍAN PASAR EL LEXICO\n");
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
            System.out.println("ARCHIVOS QUE DEBERÍAN PASAR EL SINTÁCTICO\n");
        }else {
            System.out.println("ARCHIVOS QUE NO DEBERÍAN PASAR EL SINTÁCTICO\n");
        }

        for (File file : files) {

            //Imprimo el nombre del archivo para identificarlo
            System.out.println("Resultado de prueba: " + file.getName());

            //Ejecuto con executor como se haria desde el Sintáctico
            syntacticExecutor = new SyntacticExecutor(file.getAbsolutePath(),null);
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
