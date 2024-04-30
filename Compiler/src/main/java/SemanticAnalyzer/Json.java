package SemanticAnalyzer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Clase encargada de generar el Json a partir
 * de nuestra tabla de simbolos
 * @author Yeumen Silva
 */

public class Json{

    //Concateno el principio
    private String jsonString = "{ \n \"nombre\": ";

    /**
     * Método que va a construir el Json con el formato adecuado
     * @param table Nuestra tabla de simbolos
     * @param outputPath String con ruta de salida
     * @param inputPath string con ruta de entrada
     * @return String en formato Json con todo el contenido de la tabla
     * @author Yeumen Silva
     */

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
        jsonString = jsonString.substring(0,jsonString.length()-1);
        jsonString += "\n" + "],";

        //Agrego el Start
        jsonString += addStart(tab, table);

        jsonString+= "\n" + addtabs(tab-1) + "}";
        jsonString+= "\n" + "}";


        saveJson(jsonString,inputPath);



        return this.jsonString;

    }

    /**
     * Método que dada una cantida de tabs, devuelve un string
     * con tabs
     * @param tabs int que representa cantidad de tabs
     * @return string con tabs
     * @author Yeumen Silva
     */

    private String addtabs(int tabs){
        String tabString = "";
        for (int i = 0; i < tabs; i++) {
            tabString+="\t";
        }
        return  tabString;
    }

    /**
     * Método que se encarga de añadir el start de nuestra tabla de simbolos
     * en formato json
     * @param tabs cantidad de tabs de identación
     * @param table tabla de simbolos
     * @return String con start en formato json
     * @author Yeumen Silva
     */

    private String addStart(int tabs, SymbolTable table){
        Methods start = table.getStart();
        String jsonString = "\n";
        tabs-=1;
        jsonString+=  addtabs(tabs) + "\""  + "start" + "\"" + ": {";
        tabs+=tabs+1;
        jsonString+= "\n" + addtabs(tabs) + "\"" + "nombre" + "\"" + ": " + "\"" + "start" + "\"" + ",";
        jsonString+= "\n" + addtabs(tabs) + "\"" + "retorno" + "\"" + ": " + "\"" + "void" + "\"" + ",";
        jsonString+= "\n" + addtabs(tabs) + "\"" + "posicion" + "\"" + ": " + 0 + ",";
        jsonString+= "\n" + addtabs(tabs) + "\"" + "variables" + "\"" + ": [";

        if (table.getStart().getDefinedVar().isEmpty()){
            jsonString+="]";
        }
        else {
            Map<String,Variable> variableMap = start.getDefinedVar();
            for (Map.Entry<String,Variable> variable : variableMap.entrySet()){
                jsonString +=variable.getValue().toJson(tabs+1);
            }
            jsonString = jsonString.substring(0,jsonString.length()-1);
            jsonString += "\n" + addtabs(tabs) + "]";
        }

        return jsonString;
    }

    /**
     * Método que va a guardar el Json en un archivo, almacenandolo en el mismo lugar
     * que la ruta del archivo de entrada, en formato .ts.json
     * @param jsonString string con todo el json de nuestra tabla de simbolos
     * @param inputPath string con ruta de archivo de entrada
     * @author Yeumen Silva
     */

    private void saveJson(String jsonString, String inputPath){

        String output = inputPath.replace(".ru",".ts.json");
        try {
            // Crear un FileWriter para escribir en el archivo de salida
            FileWriter escritor = new FileWriter(output);

            // Escribir la salida en el archivo
            escritor.write(jsonString);

            // Cerrar el FileWriter
            escritor.close();

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de salida: " + e.getMessage());
        }
        }

    }

