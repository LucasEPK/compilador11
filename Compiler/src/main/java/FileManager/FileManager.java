package FileManager;

import LexicalAnalyzer.Token;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * La clase FileManager realizara todo lo que tenga que ver con lectura
 * y/o escritura de archivos
 * @author Yeumen SIlva
 */
public class FileManager {

    private String inputFile, outputFile;

    private boolean byConsole = false;


    /**
     * Constructor de FileMannager
     * @param inputPath indica la ruta del archivo de entrada
     * @author Yeumen Silva
     */
    public FileManager(String inputPath){

        this.inputFile = convertFiletoString(inputPath);
        this.byConsole = true;

    }

    /**
     * Constructor de FileMannager
     * @param inputPath indica la ruta del archivo de entrada
     * @param outputPath indica la ruta del archivo de salida
     * @author Yeumen Silva
     */

    public FileManager(String inputPath, String outputPath){

        this.inputFile = convertFiletoString(inputPath);
        this.outputFile = outputPath;

    }

    /**
     * Método que dado el path del archivo de entrada, lo convierte en un
     * archivo, lo lee linea por linea, lo devuelve como un solo string y
     * le agrega un EOF
     * @param path es el String con el path al archivo
     * @author Yeumen Silva
     */
    private String convertFiletoString(String path){

        StringBuilder content = new StringBuilder();
        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            content.append("$EOF$");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public String getInputFile() {
        return inputFile;
    }

    /**
     * Método que dado una lista de tokens finales, sera el encargado
     * de escribirlos en consola o de escribirlos en un archivo en el caso
     * de que este se haya dado como parametro de entrada
     * @param tokenList lista con tokens de todo el archivo de entrada
     * @throws IOException si no puedo escribir el archivo
     * @author Yeumen Silva
     */

    public void saveResults(List<Token> tokenList){

        /*
        Llamo a función que convierte las instancias de clase token
        a string con el formato pedido
         */

        List<String> validTokens = validTokenFormat(tokenList);

        //Si no pasaron argumento de path de salida, etonces:

        if(this.byConsole){

            printByConsole(validTokens);

        }
        else {

            //De otro modo, debo crear archivo de salida

            /*
            creo objeto path a partir de cadena de texto que
            representa la ruta del archivo
             */
            Path path = Path.of(this.outputFile);

            try {

                // Verificamos si el archivo existe
                if (!Files.exists(path)) {
                    // Si no existe, lo creo
                    Files.createFile(path);
                }

                // Escribir los strings en el archivo
                Files.write(path, validTokens, StandardCharsets.UTF_8);
                System.out.println("Se han escrito los strings en el archivo exitosamente.");
            } catch (IOException e) {
                System.err.println("Error al escribir en el archivo: " + e.getMessage());
            }



        }

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




