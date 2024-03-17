package LexicalAnalyzer;

import FileManager.FileManager;

import java.util.ArrayList;
import java.util.List;


public class Executor {

    /**
     * Método que dado un String de entrada crea una instancia de la clase
     * FileMannager
     * @param inputPath String con ruta del archivo de entrada
     * @author Lucas Moyano
     * @author Yeumen Silva
     */
    public static void startExecution(String inputPath){
        List<Token> tokens = new ArrayList<Token>();

        FileManager fileManager = new FileManager(inputPath);
        String file = fileManager.getInputFile();
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(file);

        // Crea una lista de tokens utilizando el LexicalAnalyzer
        Token token = lexicalAnalyzer.getNextToken();
        while (! token.getToken().equals(lexicalAnalyzer.getEOF())){
            // no se si hay que contemplar si termina sin $EOF$, dudo
            tokens.add(token);

            token = lexicalAnalyzer.getNextToken();
        }

        tokens.add(token); // agrega token EOF a la lista de tokens
        System.out.println(tokens);
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
