package FileManager;

import java.io.File;
import java.io.IOException;

/**
 * La clase FileManager realizara todo lo que tenga que ver con lectura
 * y/o escritura de archivos
 * @author Yeumen SIlva
 */
public class FileManager {

    private File inputFile;
    private File outputFile;


    /**
     * Constructor de FileMannager
     * @param inputPath indica la ruta del archivo de entrada
     * @author Yeumen Silva
     */
    public FileManager(String inputPath){
        this.inputFile = new File(inputPath);
    }


}