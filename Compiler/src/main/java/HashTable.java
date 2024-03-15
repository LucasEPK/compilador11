
import java.util.*;
public class HashTable {

    public static void main(String[] args){

        /* Deberias copiar de aca para abajo y ponerlo en el metodo que
        declare o use esta hash table (es una libreria de java)
         */

        HashMap<String, String> hashMap = new HashMap<>();

        /*De este modo se agregan las palabras reservadas
        (HasMap.put(key,value)) esa es la sintaxis por si queres agregar o
        cambiar algo
         */

        hashMap.put("start", "start");
        hashMap.put("struct", "struct");
        hashMap.put("self", "self");
        hashMap.put("pri", "pri");
        hashMap.put("nil", "nil");
        hashMap.put("new", "new");
        hashMap.put("impl", "impl");
        hashMap.put("if", "if");
        hashMap.put("else", "else");
        hashMap.put("false", "false");
        hashMap.put("fn", "fn");
        hashMap.put("void", "void");
        hashMap.put("Array", "Array");
        hashMap.put("Str", "Str");
        hashMap.put("Bool", "Bool");
        hashMap.put("Int", "Char");

        /*Para accederlo se hace de la siguiente forma(HasMap.get(key))
        esto es solo un ejemplo en donde lo voy a printear pero vos despues
        usa el get para lo que haga falta en el lexico
         */
        System.out.println(hashMap.get("Bool"));


    }
}
