package LexicalAnalyzer;

import java.util.ArrayList;
import java.util.List;

public class LexicalAnalyzer {
    //Acá se definen todos los tokens como constantes para simplificar el cambio de nombres
    private final String NEW_LINE = "NewLine";
    private final String STRUCT_ID = "StructID";
    private final String OBJ_ID = "ObjID";
    private final String INT_LITERAL = "IntLiteral";
    private final String CLOSE_BRACKET = "CloseBracket";
    private final String OPEN_BRACKET = "OpenBracket";
    private final String CLOSE_PARENTHESIS = "CloseParenthesis";
    private final String OPEN_PARENTHESIS = "OpenParenthesis";
    private final String CLOSE_BRACES = "CloseBraces";
    private final String OPEN_BRACES = "OpenBraces";
    private final String SEMI_COLON = "SemiColon";
    private final String COLON = "Colon";
    private final String COMMA = "Comma";
    private final String PERIOD = "Period";
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
        System.out.println(tokens);
    }

    /**
     * Este es el comienzo del automata
     * @author Lucas Moyano
     * @param file es un string que tiene todo el contenido del archivo que se se está leyendo
     * */
    private String s0(String file){
        String token = null;
        char currentChar = file.charAt(currentPos);


        // Acá se hace return en vez de darle valor al token y esperar el ultimo return
        // para evitar que tire error al no matchear en el switch
        if (currentChar >= 'A' && currentChar <= 'Z'){
            currentPos += 1;
            token = structID(file);
            return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
        }
        else {
            if(currentChar >= 'a' && currentChar <= 'z'){
                currentPos += 1;
                token = objID(file);
                return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
            }
            else {
                if(currentChar >= '0' && currentChar <= '9'){
                    currentPos += 1;
                    token = intLiteral(file);
                    return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
                }
                else {
                    if(currentChar == '*' || currentChar == '/' || currentChar == '%'){
                        return "bruh";
                    }
                }
            }
        }

        switch (currentChar) {
            case '\\':
                currentPos += 1;
                token = s1(file);
                break;
            case '\"':
                break;
            case '\'':
                break;
            case '=':
                break;
            case '!':
                break;
            case '&':
                break;
            case '|':
                break;
            case '<':
                break;
            case '>':
                break;
            case '+':
                break;
            case '-':
                break;
            case ']':
                token = CLOSE_BRACKET;
                currentPos += 1;
                break;
            case '[':
                token = OPEN_BRACKET;
                currentPos += 1;
                break;
            case ')':
                token = CLOSE_PARENTHESIS;
                currentPos += 1;
                break;
            case '(':
                token = OPEN_PARENTHESIS;
                currentPos += 1;
                break;
            case '}':
                token = CLOSE_BRACES;
                currentPos += 1;
                break;
            case '{':
                token = OPEN_BRACES;
                currentPos += 1;
                break;
            case ';':
                token = SEMI_COLON;
                currentPos += 1;
                break;
            case ',':
                token = COMMA;
                currentPos += 1;
                break;
            case '.':
                token = PERIOD;
                currentPos += 1;
                break;
            case ':':
                token = COLON;
                currentPos += 1;
                break;
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
     * Estado aceptador del automata tiene 3 casos
     * 1. Le sigue una letra mayuscula o minuscula
     * 2. Le sigue un numero o un guión bajo
     * 3. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String structID(String file){
        String token = STRUCT_ID; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')){
            currentPos += 1;
            token = structID(file);
        }
        else {
            if ((currentChar >= '0' && currentChar <= '9') || currentChar == '_') {
                currentPos += 1;
                token = s2(file);
            }
            else{
                // Este es el caso donde miramos más caracteres de lo que deberiamos,
                // por ende no tiramos error ni aumentamos el currentPos
            }
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue una letra mayuscula, minuscula, un numero o un guión bajo
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String objID(String file){
        String token = OBJ_ID; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')
                || (currentChar >= '0' && currentChar <= '9') || currentChar == '_'){
            currentPos += 1;
            token = objID(file);
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un numero
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String intLiteral(String file){
        String token = INT_LITERAL; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if (currentChar >= '0' && currentChar <= '9'){
            currentPos += 1;
            token = intLiteral(file);
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
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
        char currentChar = file.charAt(currentPos);

        switch (currentChar) {
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
     * Estado donde un structID tiene numeros o guión bajo,
     * como no puede terminar en estos no es aceptador
     * @author Lucas Moyano
     * */
    private String s2(String file){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')){
            currentPos += 1;
            token = structID(file);
        }
        else {
            if ((currentChar >= '0' && currentChar <= '9') || currentChar == '_') { // bucle
                currentPos += 1;
                token = s2(file);
            }
            else {
                // TODO: tirar error
            }
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
        char currentChar = file.charAt(currentPos);

        switch (currentChar) {
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
        char currentChar = file.charAt(currentPos);

        switch (currentChar) {
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
        char currentChar = file.charAt(currentPos);

        switch (currentChar) {
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
        char currentChar = file.charAt(currentPos);

        switch (currentChar) {
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
