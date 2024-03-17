package LexicalAnalyzer;

import FileManager.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Executor {

    /**
     * Método que dado un String de entrada crea una instancia de la clase
     * FileMannager
     * @param inputPath String con ruta del archivo de entrada
     * @author Lucas Moyano
     * @author Yeumen Silva
     */
    public void startExecution(String inputPath){
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


        printResults(tokens); // Imprimo reusltados por consola



    }

    /**
     * Método que dado un String de entrada crea una instancia de la clase
     * FileMannager
     * @param inputPath String con ruta del archivo de entrada
     * @param outputPath String con ruta del archivo de salida
     */
    public void startExecution(String inputPath, String outputPath){
        FileManager fileManager = new FileManager(inputPath, outputPath);

        List<Token> tokens = new ArrayList<Token>();
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


        //Guardo resultados en archivo de salida
        fileManager.saveResults(validTokenFormat(tokens));

    }

    /**
     * Método que dado una lista de tokens finales, sera el encargado
     * de escribirlos en consola o de escribirlos en un archivo en el caso
     * de que este se haya dado como parametro de entrada
     * @param tokenList lista con tokens de todo el archivo de entrada
     * @throws IOException si no puedo escribir el archivo
     * @author Yeumen Silva
     */

    private void printResults(List<Token> tokenList) {

        /*
        Llamo a función que convierte las instancias de clase token
        a string con el formato pedido
         */

        List<String> validTokens = validTokenFormat(tokenList);

        //Si no pasaron argumento de path de salida, etonces:

        printByConsole(validTokens);


    }

    /**
     * Método que dado una lista de tokens finales, los convierte a string
     * en el formato indicado por el documento de entrega
     * @param tokenList lista con tokens de todo el archivo de entrada
     * @author Yeumen Silva
     * @author Lucas Moyano
     */

    private List<String> validTokenFormat(List<Token> tokenList){

        //Declaro lista de strings

        List<String> validStrings = new ArrayList<>();

        // Agrego encabezado
        validStrings.add("CORRECTO: ANALISIS LEXICO");
        validStrings.add("| TOKEN | LEXEMA | NUMERO DE LINEA (NUMERO DE COLUMNA) |");

        /*
        Recorro lista de tokens y los guardo en una lista con el
        formato pedido
         */

        for (Token token : tokenList) {

            validStrings.add("| " + token.getToken() + " | " + token.getLexeme() +
                    " | LINEA " + token.getRow() + " (COLUMNA " + token.getColumn() + ") |");

        }

        return validStrings;

    }

    /**
     * Método que dado una lista de strings con formato solicitado,
     * escribe por consola los resultados
     * @param stringTokens lista con strings
     *en formato pedido(token,lexeme,row,column)
     * @author Yeumen Silva
     */
    private void printByConsole(List<String> stringTokens){

        //Imprimo reocrriendo la lista

        for(String tokenString : stringTokens){
            System.out.println(tokenString);
        }

    }
}
