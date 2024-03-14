import FileManager.FileManager;

import java.io.*;

public class Main {

    public static void main(String[] args) {

        String inputPath;
        String outputPath;
        FileManager fileManager;

        try {
            inputPath = args[0];
        }
        catch (Exception e){
            //System.err.println("Sin argumentos de entrada");
            //e.printStackTrace();
            //System.exit(-1);
            //return;
            throw new RuntimeException("No tenes argumentos", e);
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
