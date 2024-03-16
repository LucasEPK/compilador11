package LexicalAnalyzer;

import FileManager.FileManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Executor {

    /**
     * Método que dado un String de entrada crea una instancia de la clase
     * FileMannager
     * @param inputPath String con ruta del archivo de entrada
     */
    public static void startExecution(String inputPath){
        List<String> tokens = new ArrayList<String>();

        FileManager fileManager = new FileManager(inputPath);
        String file = fileManager.getInputFile();
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();


        String token = lexicalAnalyzer.getToken(file);
        while (! token.equals(lexicalAnalyzer.getEOF())){
            // no se si hay que contemplar si termina sin $EOF$, dudo
            tokens.add(token);

            token = lexicalAnalyzer.getToken(file);
        }

        tokens.add(token); // agrega token EOF a la lista de tokens
        System.out.println(tokens);

        /*

        Por si queres probar que se imprime la salida por consola

        Token token1 = new Token();
        Token token2 = new Token();

        token1.setToken("ObjectId");
        token1.setLexeme("hola");
        token1.setRow(1);
        token1.setColumn(2);

        token2.setToken("struct");
        token2.setLexeme("struct");
        token2.setRow(3);
        token2.setColumn(4);

        List<Token> tokenList = new ArrayList<>();

        tokenList.add(token1);
        tokenList.add(token2);

        fileManager.saveResults(tokenList);

         */

    }

    /**
     * Método que dado un String de entrada crea una instancia de la clase
     * FileMannager
     * @param inputPath String con ruta del archivo de entrada
     * @param outputPath String con ruta del archivo de salida
     */
    public static void startExecution(String inputPath, String outputPath){

        FileManager fileManager = new FileManager(inputPath, outputPath);

        /*

        Para probar que se escribe el archivo}

        Token token1 = new Token();
        Token token2 = new Token();

        token1.setToken("ObjectId");
        token1.setLexeme("hola");
        token1.setRow(1);
        token1.setColumn(2);

        token2.setToken("struct");
        token2.setLexeme("struct");
        token2.setRow(3);
        token2.setColumn(4);

        List<Token> tokenList = new ArrayList<>();

        tokenList.add(token1);
        tokenList.add(token2);

        fileManager.saveResults(tokenList);

         */




    }
}
