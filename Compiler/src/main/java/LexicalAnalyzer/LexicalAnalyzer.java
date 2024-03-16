package LexicalAnalyzer;

import java.util.ArrayList;
import java.util.List;
// Estos 2 import sirven para crear el alfabeto como conjunto
import java.util.HashSet;
import java.util.Set;

public class LexicalAnalyzer {
    //Acá se definen todos los tokens como constantes para simplificar el cambio de nombres
    private final String BLANK_SPACE = "BlankSpace";
    private final String NEW_LINE = "NewLine";
    private final String CARRIAGE_RETURN = "CarriageReturn";
    private final String TAB = "Tab";
    private final String VERTICAL_TAB = "VerticalTab";
    private final String SIMPLE_COMMENT = "SimpleComment";
    private final String STRUCT_ID = "StructID";
    private final String OBJ_ID = "ObjID";
    private final String INT_LITERAL = "IntLiteral";
    private final String STR_LITERAL = "StrLiteral";
    private final String CHAR_LITERAL = "CharLiteral";
    private final String ASSIGNMENT = "Assignment";
    private final String EQUAL = "Equal";
    private final String LOGICAL = "Logical";
    private final String COMPARISON = "Comparison";
    private final String OP_AD = "OpAd";
    private final String OP_UNARY = "OpUnary";
    private final String ARROW = "Arrow";
    private final String OP_MUL = "OpMul";
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

    private String file;

    /**
     * Constructor que define el archivo que vamos a leer
     * @author Lucas Moyano
     * @param file este es el archivo que queremos leer pasado a String
     * */
    public LexicalAnalyzer(String file){
        this.file = file;
    }

    /**
     * Empieza el automata
     * */
    public String getToken(){
        return s0();
    }

    /**
     * Este es el comienzo del automata
     * @author Lucas Moyano
     * */
    private String s0(){
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
                        token = OP_MUL;
                        currentPos += 1;
                        return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
                    }
                }
            }
        }

        switch (currentChar) {
            case ' ':
                token = BLANK_SPACE;
                currentPos += 1;
                break;
            case '\n':
                token = NEW_LINE;
                currentPos += 1;
                break;
            case '\r':
                token = CARRIAGE_RETURN;
                currentPos += 1;
                break;
            case '\t':
                token = TAB;
                currentPos += 1;
                break;
/*            case '\v':
                token = VERTICAL_TAB;
                currentPos += 1;
                break;*/
            case '\\':
                currentPos += 1;
                token = s1(file);
                break;
            case '\"':
                currentPos += 1;
                token = s3(file);
                break;
            case '\'':
                currentPos += 1;
                token = s5(file);
                break;
            case '=':
                currentPos += 1;
                token = assignment(file);
                break;
            case '!':
                currentPos += 1;
                token = logical(file);
                break;
            case '&':
                currentPos += 1;
                token = s34(file);
                break;
            case '|':
                currentPos += 1;
                token = s35(file);
                break;
            case '<':
                currentPos += 1;
                token = comparison(file);
                break;
            case '>':
                currentPos += 1;
                token = comparison(file);
                break;
            case '+':
                currentPos += 1;
                token = opAd(file);
                break;
            case '-':
                currentPos += 1;
                token = opAd2(file);
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
    private String structID(){
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
    private String objID(){
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
    private String intLiteral(){
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
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un = (en este caso es aceptado como un token Equal)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String assignment(){
        String token = ASSIGNMENT; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if (currentChar == '='){
            currentPos += 1;
            token = EQUAL;
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un = (en este caso es aceptado como un token Equal)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String logical(){
        String token = LOGICAL; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if (currentChar == '='){
            currentPos += 1;
            token = EQUAL;
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un = (en este caso es aceptado como un token Comparison)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String comparison(){
        String token = COMPARISON; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if (currentChar == '='){
            currentPos += 1;
            token = COMPARISON;
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un + (en este caso es aceptado como un token OpUnary)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String opAd(){
        String token = OP_AD; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if (currentChar == '+'){
            currentPos += 1;
            token = OP_UNARY;
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 3 casos
     * 1. Le sigue un - (en este caso es aceptado como un token OpUnary)
     * 2. Le sigue un > (es un token Arrow)
     * 3. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * */
    private String opAd2(){
        String token = OP_AD; // Esto es porque es un estado aceptador
        char currentChar = file.charAt(currentPos);

        if (currentChar == '-'){
            currentPos += 1;
            token = OP_UNARY;
        }
        else {
            if (currentChar == '>'){
                currentPos += 1;
                token = ARROW;
            }
            else {
                // Este es el caso donde miramos más caracteres de lo que deberiamos,
                // por ende no tiramos error ni aumentamos el currentPos
            }
        }

        return token;
    }


    /**
     * Con este metodo se entra en los nodos del automata que corresponden
     * a lexemas que empiezan con \
     * @author Lucas Moyano
     * */
    private String s1(){
        String token = null;
        char currentChar = file.charAt(currentPos);

        //TODO: hace algo con este switch xd
        switch (currentChar) {
            case '?':
                currentPos += 1;
                token = s54(file);
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
    private String s2(){
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
     * Estado donde empieza, hace bucle y termina un string literal
     * @author Lucas Moyano
     * */
    private String s3(){
        //TODO: tiene que tener un limite de 1024 caracteres
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (currentChar == '"'){
            currentPos += 1;
            token = STR_LITERAL;
        }
        else {
            if (belongsToTheAlphabet(currentChar, '"')) { // bucle
                currentPos += 1;
                token = s3(file);
            }
            else {
                // TODO: tirar error
            }
        }

        return token;
    }

    /**
     * Estado donde empieza un character literal
     * @author Lucas Moyano
     * */
    private String s5(){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (currentChar == '\\'){
            currentPos += 1;
            token = s7(file);
        }
        else {
            if (belongsToTheAlphabet(currentChar, '\'')) {
                currentPos += 1;
                token = s6(file);
            }
            else {
                // TODO: tirar error
            }
        }

        return token;
    }

    /**
     * Estado donde termina un character literal
     * @author Lucas Moyano
     * */
    private String s6(){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (currentChar == '\''){
            currentPos += 1;
            token = CHAR_LITERAL;
        }
        else {
            // TODO: tirar error
        }

        return token;
    }

    /**
     * Estado intermedio de un character literal
     * @author Lucas Moyano
     * */
    private String s7(){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (belongsToTheAlphabet(currentChar, '0')){
            currentPos += 1;
            token = s6(file);
        }
        else {
            // TODO: tirar error
        }

        return token;
    }

    /**
     * Estado de entrada de un Logical2
     * @author Lucas Moyano
     * */
    private String s34(){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (currentChar == '&'){
            currentPos += 1;
            token = LOGICAL;
        }
        else {
            // TODO: tirar error
        }

        return token;
    }

    /**
     * Estado de entrada de un Logical2
     * @author Lucas Moyano
     * */
    private String s35(){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (currentChar == '|'){
            currentPos += 1;
            token = LOGICAL;
        }
        else {
            // TODO: tirar error
        }

        return token;
    }

    /**
     * Con este metodo se entra en los nodos del automata
     * que van a terminar en $EOF$
     * @author Lucas Moyano
     * */
    private String s50() {
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

    /**
     * Estado donde empieza un comentario
     * @author Lucas Moyano
     * */
    private String s54(String file){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (currentChar == '\\'){
            currentPos += 1;
            token = s55(file);
        }
        else {
            if (belongsToTheAlphabet(currentChar)) { // bucle
                currentPos += 1;
                token = s54(file);
            }
            else {
                // TODO: tirar error
            }
        }

        return token;
    }

    /**
     * Estado intermedio y donde termina un comentario
     * @author Lucas Moyano
     * */
    private String s55(String file){
        String token = null;
        char currentChar = file.charAt(currentPos);

        if (currentChar == '\\'){
            currentPos += 1;
            token = s55(file);
        }
        else {
            if (currentChar == 'n'){
                currentPos += 1;
                token = SIMPLE_COMMENT;
            }
            else {
                if (belongsToTheAlphabet(currentChar)) { // bucle
                    currentPos += 1;
                    token = s54(file);
                }
                else {
                    // TODO: tirar error
                }
            }
        }

        return token;
    }

    // ----------------------- FUNCIONES AUXILIARES --------------------------------------

    /**
     * Este metodo nos dice si un caracter pertenece o no a nuestro alfabeto
     * @param element este es el caracter del cual queremos saber si pertenece o no al alfabeto
     * @author Lucas Moyano
     * */
    private boolean belongsToTheAlphabet(char element) {
        // Crea un conjunto para almacenar los caracteres del alfabeto
        Set<Character> sigma = new HashSet<>();

        // Esta variable trackea si el element pertenece o no al conjunto
        boolean belongs = false;

        // Agrega los caracteres al conjunto
        for (char c = 'a'; c <= 'z'; c++) {
            sigma.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            sigma.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            sigma.add(c);
        }

        // Agregar los caracteres especiales al conjunto
        char[] caracteresEspeciales = {'\\', '?', '#', '$', '%', '&', '@', '¿', '¡', '!', 'ñ', '+', '-', '*', '/', '_', '>', '<', '"', '\'', '.', ';', ':', 'á', 'é', 'í', 'ó', 'ú', 'Á', 'É', 'Í', 'Ó', 'Ú', '(', ')', '[', ']', '{', '}', ','};
        for (char c : caracteresEspeciales) {
            sigma.add(c);
        }

        // Verifica si un elemento pertenece al conjunto
        if (sigma.contains(element)) {
            belongs = true;
        }

        return belongs;
    }

    /**
     * Este metodo nos dice si un caracter pertenece o no a nuestro alfabeto
     * con un caracter de excepción
     * @param element este es el caracter del cual queremos saber si pertenece o no al alfabeto
     * @param exception este es el caracter que queremos excluir del alfabeto
     * @author Lucas Moyano
     * */
    private boolean belongsToTheAlphabet(char element, char exception) {
        // Crea un conjunto para almacenar los caracteres del alfabeto
        Set<Character> sigma = new HashSet<>();

        // Esta variable trackea si el element pertenece o no al conjunto
        boolean belongs = false;

        // Agrega los caracteres al conjunto
        for (char c = 'a'; c <= 'z'; c++) {
            sigma.add(c);
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            sigma.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            sigma.add(c);
        }

        // Agregar los caracteres especiales al conjunto
        char[] caracteresEspeciales = {'\\', '?', '#', '$', '%', '&', '@', '¿', '¡', '!', 'ñ', '+', '-', '*', '/', '_', '>', '<', '"', '\'', '.', ';', ':', 'á', 'é', 'í', 'ó', 'ú', 'Á', 'É', 'Í', 'Ó', 'Ú', '(', ')', '[', ']', '{', '}', ','};
        for (char c : caracteresEspeciales) {
            sigma.add(c);
        }

        // Removemos la excepcion del conjunto
        sigma.remove(exception);

        // Verifica si un elemento pertenece al conjunto
        if (sigma.contains(element)) {
            belongs = true;
        }

        return belongs;
    }

    public String getEOF() {
        return EOF;
    }
}
