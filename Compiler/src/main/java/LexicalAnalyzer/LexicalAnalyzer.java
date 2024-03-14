package LexicalAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer {
    //Acá se definen todos los tokens como constantes para simplificar el cambio de nombres
    private final String NEW_LINE = "NewLine";
    private final String EOF = "$EOF$";

    private int currentPos = 0;
    public LexicalAnalyzer (String file){
        List<String> tokens = new ArrayList<String>();

        String token = s0(file);
        while (! token.equals(EOF)){
            // no se si hay que contemplar si termina sin $EOF$, dudo
            tokens.add(token);

            token = s0(file);
        }

        tokens.add(token); // agrega token EOF a la lista de tokens
    }

    /**
     * Este es el comienzo del automata
     * @author Lucas Moyano
     * @param file es un string que tiene todo el contenido del archivo que se se está leyendo
     * */
    private String s0(String file){
        String token = null;
        char currentChar = file.charAt(currentPos);

        // TODO: hacerlo con switch e if me parece va a ser la mejor forma
        if (currentChar == '\\') {
            currentPos += 1;
            token = s1(file);
        }
        else {
            if(currentChar >= 'A' && currentChar <= 'Z'){
                int bruh;
            }
            else {
                if(currentChar >= 'a' && currentChar <= 'z'){
                    int bruh;
                }
                else {
                    if(currentChar >= '0' && currentChar <= '9'){
                        int bruh;
                    }
                }
            }
        }
        switch (file.charAt(currentPos)) {
            case '\\':
                currentPos += 1;
                token = s1(file);
                break;
            case 'A':
                break;
            case 'B':
                break;
            case 'C':

            case '$':
                currentPos += 1;
                token = s50(file);
                break;
            default:
                //TODO: Acá tendria que largar error porque no se encontró un caracter valido
        }
        return token;
    }

    /**
     * Con este metodo se entra en los nodos del automata que corresponden
     * a lexemas que empiezan con \
     * @author Lucas Moyano
     * */
    private String s1(String file){
        String token = null;
        switch (file.charAt(currentPos)) {
            case 'n':
                token = NEW_LINE;
                currentPos += 1;
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Con este metodo se entra en los nodos del automata
     * que van a terminar en $EOF$
     * @author Lucas Moyano
     * */
    private String s50(String file) {
        String token = null;
        switch (file.charAt(currentPos)) {
            case 'E':
                currentPos += 1;
                token = s51(file);
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Camino hacia $EOF$
     * @author Lucas Moyano
     * */
    private String s51(String file) {
        String token = null;
        switch (file.charAt(currentPos)) {
            case 'O':
                currentPos += 1;
                token = s52(file);
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Camino hacia $EOF$
     * @author Lucas Moyano
     * */
    private String s52(String file) {
        String token = null;
        switch (file.charAt(currentPos)) {
            case 'F':
                currentPos += 1;
                token = s53(file);
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Camino hacia $EOF$
     * @author Lucas Moyano
     * */
    private String s53(String file) {
        String token = null;
        switch (file.charAt(currentPos)) {
            case '$':
                token = EOF;
                currentPos += 1;
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

}
