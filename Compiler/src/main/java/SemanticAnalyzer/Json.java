package SemanticAnalyzer;

import java.util.HashMap;
import java.util.Map;

public class Json {

    //Concateno el principio
    private String jsonString = "{ \n \"nombre\": ";


    public String buildJson(SymbolTable table, String outputPath, String inputPath){

        int tab = 2;
        //Concateno nombre del archivo y structs
        this.jsonString = this.jsonString + "\"" + inputPath + "\"\n" + "\"structs\": [\n\t{";


        Map<String,Struct> structsHash = table.getStructs();

        /*Por cada struct de la tabla hago un llamado a mi función que
        va a devolver el formato Json
         */
        for(Map.Entry<String,Struct> structs : structsHash.entrySet() ){

            jsonString += structs.getValue().toJson(tab);
        }

        System.out.println(jsonString);

        //ToDo agregar start

        return this.jsonString;

    }

}
