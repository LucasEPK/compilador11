package LexicalAnalyzer;

import FileManager.FileManager;


public class Executor {

    /**
     * Método que dado un String de entrada crea una instancia de la clase
     * FileMannager
     * @param inputPath String con ruta del archivo de entrada
     */
    public static void startExecution(String inputPath){

        FileManager fileManager = new FileManager(inputPath);
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(fileManager.getInputFile());
    }

    /**
     * Método que dado un String de entrada crea una instancia de la clase
     * FileMannager
     * @param inputPath String con ruta del archivo de entrada
     * @param outputPath String con ruta del archivo de salida
     */
    public static void startExecution(String inputPath, String outputPath){

        FileManager fileManager = new FileManager(inputPath, outputPath);


    }
}
