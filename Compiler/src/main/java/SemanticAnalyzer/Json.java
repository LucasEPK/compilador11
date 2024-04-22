package SemanticAnalyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Json extends Commons {

    //Concateno el principio
    private String jsonString = "{ \n \"nombre\": ";


    public String buildJson(SymbolTable table, String outputPath, String inputPath){

        int tab = 2;
        //Concateno nombre del archivo y structs
        this.jsonString = this.jsonString + "\"" + inputPath + "\"" + "," + "\n" + "\"structs\": [";


        Map<String,Struct> structsHash = table.getStructs();

        /*Por cada struct de la tabla hago un llamado a mi función que
        va a devolver el formato Json
         */
        for(Map.Entry<String,Struct> structs : structsHash.entrySet() ){
            jsonString += "\n \t {";
            jsonString += structs.getValue().toJson(tab);
            jsonString += "\n" + addtabs(tab-1) + "},";
        }
        //ToDo agregar start
        //ToDo agregar temas de herencia para que agrege metodos y atributos
        //ToDo agregar constructores
        jsonString = jsonString.substring(0,jsonString.length()-1);
        jsonString += "\n" + "]";
        jsonString += "\n" + "}";


        saveJson(jsonString,inputPath);



        return this.jsonString;

    }

    private void saveJson(String jsonString, String inputPath){

        String output = inputPath.replace(".ru",".ts.json");
        System.out.println(output);
        try {
            // Crear un FileWriter para escribir en el archivo de salida
            FileWriter escritor = new FileWriter(output);

            // Escribir la salida en el archivo
            escritor.write(jsonString);

            // Cerrar el FileWriter
            escritor.close();

            System.out.println("Archivo de salida guardado exitosamente como: " + output);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de salida: " + e.getMessage());
        }
        }

    }

