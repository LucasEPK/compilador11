import Exceptions.NoArgsException;
import FileManager.FileManager;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        String inputPath;
        String outputPath;
        FileManager fileManager;

        try {
            if (args.length == 0) {
                throw new NoArgsException();
            }
            inputPath = args[0];
        } catch (NoArgsException e) {
            System.err.println(e.getExceptionType());
            return;
        }



        if (args.length < 2){

            fileManager = new FileManager(inputPath);
        }
        else    {

            outputPath = args[1];
            fileManager = new FileManager(inputPath, outputPath);
            File file = new File(inputPath);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


        };




    }

}
