package FileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * La clase FileManager realizara todo lo que tenga que ver con lectura
 * y/o escritura de archivos
 * @author Yeumen SIlva
 */
public class FileManager {

    private String inputFile;
    private String outputFile;

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
            content.append("[EOF]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}




