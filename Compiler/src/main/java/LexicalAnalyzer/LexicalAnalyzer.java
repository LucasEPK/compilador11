package LexicalAnalyzer;

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
    private int currentRow = 1;
    private int currentColumn = 0;

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
    public Token getNextToken(){
        return s0();
    }

    /**
     * Este es el comienzo del automata
     * @author Lucas Moyano
     * */
    private Token s0(){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = Character.toString(currentChar);

        currentColumn += 1;


        // Acá se hace return en vez de darle valor al token y esperar el ultimo return
        // para evitar que tire error al no matchear en el switch
        if (currentChar >= 'A' && currentChar <= 'Z'){
            currentPos += 1;
            token = structID(currentLexeme);
            return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
        }
        else {
            if(currentChar >= 'a' && currentChar <= 'z'){
                currentPos += 1;
                token = objID(currentLexeme);
                return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
            }
            else {
                if(currentChar >= '0' && currentChar <= '9'){
                    currentPos += 1;
                    token = intLiteral(currentLexeme);
                    return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
                }
                else {
                    if(currentChar == '*' || currentChar == '%'){
                        token = new Token(OP_MUL, currentLexeme, currentRow,
                                startColumn(currentColumn, currentLexeme.length()));
                        currentPos += 1;
                        return token; // TODO: ESTOS RETURN NO CUMPLEN CON LAS REGLAS DE CODIFICACION
                    }
                }
            }
        }

        switch (currentChar) {
            case ' ':
                token = new Token(BLANK_SPACE, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '\n':
                token = new Token(NEW_LINE, "\\n", currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;

                currentColumn = 0;
                currentRow += 1;
                break;
            case '\r':
                token = new Token(CARRIAGE_RETURN, "\\r", currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '\t':
                token = new Token(TAB, "\\t", currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
/*            case '\v':
                token = new Token(VERTICAL_TAB, "\\v", currentRow,
                startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;*/
            case '/':
                currentPos += 1;
                token = opMul2(currentLexeme);
                break;
            case '\"':
                currentPos += 1;
                token = s3(currentLexeme);
                break;
            case '\'':
                currentPos += 1;
                token = s5(currentLexeme);
                break;
            case '=':
                currentPos += 1;
                token = assignment(currentLexeme);
                break;
            case '!':
                currentPos += 1;
                token = logical(currentLexeme);
                break;
            case '&':
                currentPos += 1;
                token = s34(currentLexeme);
                break;
            case '|':
                currentPos += 1;
                token = s35(currentLexeme);
                break;
            case '<':
                currentPos += 1;
                token = comparison(currentLexeme);
                break;
            case '>':
                currentPos += 1;
                token = comparison(currentLexeme);
                break;
            case '+':
                currentPos += 1;
                token = opAd(currentLexeme);
                break;
            case '-':
                currentPos += 1;
                token = opAd2(currentLexeme);
                break;
            case ']':
                token = new Token(CLOSE_BRACKET, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '[':
                token = new Token(OPEN_BRACKET, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case ')':
                token = new Token(CLOSE_PARENTHESIS, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '(':
                token = new Token(OPEN_PARENTHESIS, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '}':
                token = new Token(CLOSE_BRACES, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '{':
                token = new Token(OPEN_BRACES, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case ';':
                token = new Token(SEMI_COLON, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case ',':
                token = new Token(COMMA, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '.':
                token = new Token(PERIOD, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case ':':
                token = new Token(COLON, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            case '$':
                currentPos += 1;
                token = s50(currentLexeme);
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
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token structID(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = structID(currentLexeme);
        }
        else {
            if ((currentChar >= '0' && currentChar <= '9') || currentChar == '_') {
                currentPos += 1;
                currentLexeme = lexeme + Character.toString(currentChar);
                token = s2(currentLexeme);
            }
            else{
                // Este es el caso donde miramos más caracteres de lo que deberiamos,
                // por ende no tiramos error ni aumentamos el currentPos
                // y dejamos el lexema como estaba sin agregar caracteres
                currentLexeme = lexeme;
                currentColumn -= 1;
                token = new Token(STRUCT_ID, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
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
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token objID(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')
                || (currentChar >= '0' && currentChar <= '9') || currentChar == '_'){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = objID(currentLexeme);
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
            // y dejamos el lexema como estaba sin agregar caracteres
            currentLexeme = lexeme;
            currentColumn -= 1;
            token = new Token(OBJ_ID, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un numero
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token intLiteral(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if (currentChar >= '0' && currentChar <= '9'){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = intLiteral(currentLexeme);
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
            // y dejamos el lexema como estaba sin agregar caracteres
            currentLexeme = lexeme;
            currentColumn -= 1;
            token = new Token(INT_LITERAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un = (en este caso es aceptado como un token Equal)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token assignment(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if (currentChar == '='){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = new Token(EQUAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
            // y dejamos el lexema como estaba sin agregar caracteres
            currentLexeme = lexeme;
            currentColumn -= 1;
            token = new Token(ASSIGNMENT, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un = (en este caso es aceptado como un token Equal)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token logical(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if (currentChar == '='){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = new Token(EQUAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
            // y dejamos el lexema como estaba sin agregar caracteres
            currentLexeme = lexeme;
            currentColumn -= 1;
            token = new Token(LOGICAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un = (en este caso es aceptado como un token Comparison)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token comparison(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if (currentChar == '='){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = new Token(COMPARISON, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
            // y dejamos el lexema como estaba sin agregar caracteres
            currentLexeme = lexeme;
            currentColumn -= 1;
            token = new Token(COMPARISON, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue un + (en este caso es aceptado como un token OpUnary)
     * 2. Ninguno de los anteriores (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token opAd(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if (currentChar == '+'){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = new Token(OP_UNARY, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
            // y dejamos el lexema como estaba sin agregar caracteres
            currentLexeme = lexeme;
            currentColumn -= 1;
            token = new Token(OP_AD, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
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
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token opAd2(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if (currentChar == '-'){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = new Token(OP_UNARY, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            if (currentChar == '>'){
                currentPos += 1;
                currentLexeme = lexeme + Character.toString(currentChar);
                token = new Token(ARROW, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
            }
            else {
                // Este es el caso donde miramos más caracteres de lo que deberiamos,
                // por ende no tiramos error ni aumentamos el currentPos
                // y dejamos el lexema como estaba sin agregar caracteres
                currentLexeme = lexeme;
                currentColumn -= 1;
                token = new Token(OP_AD, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
            }
        }

        return token;
    }

    /**
     * Estado aceptador del automata tiene 2 casos
     * 1. Le sigue una signo de pregunta
     * 2. Leemos un caracter de más (en este caso no arroja error sino que
     * deslee el char para que sea analizado de vuelta desde el principio)
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token opMul2(String lexeme){
        Token token;
        String currentLexeme;
        char currentChar = file.charAt(currentPos);

        currentColumn += 1;

        if (currentChar == '?'){
            currentPos += 1;
            currentLexeme = lexeme + Character.toString(currentChar);
            token = s54(currentLexeme);
        }
        else {
            // Este es el caso donde miramos más caracteres de lo que deberiamos,
            // por ende no tiramos error ni aumentamos el currentPos
            // y dejamos el lexema como estaba sin agregar caracteres
            currentLexeme = lexeme;
            currentColumn -= 1;
            token = new Token(OP_MUL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        return token;
    }

    /**
     * Estado donde un structID tiene numeros o guión bajo,
     * como no puede terminar en estos no es aceptador
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s2(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')){
            currentPos += 1;
            token = structID(currentLexeme);
        }
        else {
            if ((currentChar >= '0' && currentChar <= '9') || currentChar == '_') { // bucle
                currentPos += 1;
                token = s2(currentLexeme);
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
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s3(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        int charCount = currentLexeme.length() - 2; // Se hace -2 por las comillas
        if (charCount > 1024){ // Nos fijamos que no hayan más de 1024 caracteres entre las comillas
            //TODO: tirar error
        }


        currentColumn += 1;

        if (currentChar == '"'){
            currentPos += 1;
            token = new Token(STR_LITERAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            if (belongsToTheAlphabet(currentChar, '"')) { // bucle
                currentPos += 1;
                token = s3(currentLexeme);
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
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s5(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        if (currentChar == '\\'){
            currentPos += 1;
            token = s7(currentLexeme);
        }
        else {
            if (belongsToTheAlphabet(currentChar, '\'')) {
                currentPos += 1;
                token = s6(currentLexeme);
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
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s6(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        if (currentChar == '\''){
            currentPos += 1;
            token = new Token(CHAR_LITERAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            // TODO: tirar error
        }

        return token;
    }

    /**
     * Estado intermedio de un character literal
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s7(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        if (belongsToTheAlphabet(currentChar, '0')){
            currentPos += 1;
            token = s6(currentLexeme);
        }
        else {
            // TODO: tirar error
        }

        return token;
    }

    /**
     * Estado de entrada de un Logical2
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s34(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        if (currentChar == '&'){
            currentPos += 1;
            token = new Token(LOGICAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
        }
        else {
            // TODO: tirar error
        }

        return token;
    }

    /**
     * Estado de entrada de un Logical2
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s35(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        if (currentChar == '|'){
            currentPos += 1;
            token = new Token(LOGICAL, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()));
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
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s50(String lexeme) {
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        switch (currentChar) {
            case 'E':
                currentPos += 1;
                token = s51(currentLexeme);
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Camino hacia $EOF$
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s51(String lexeme) {
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        switch (currentChar) {
            case 'O':
                currentPos += 1;
                token = s52(currentLexeme);
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Camino hacia $EOF$
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s52(String lexeme) {
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        switch (currentChar) {
            case 'F':
                currentPos += 1;
                token = s53(currentLexeme);
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Camino hacia $EOF$
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s53(String lexeme) {
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme = lexeme + Character.toString(currentChar);

        currentColumn += 1;

        switch (currentChar) {
            case '$':
                token = new Token(EOF, currentLexeme, currentRow,
                        startColumn(currentColumn, currentLexeme.length()));
                currentPos += 1;
                break;
            default:
                // TODO: acá debería arrojar error
        }

        return token;
    }

    /**
     * Estado donde empieza y termina un comentario
     * @author Lucas Moyano
     * @param lexeme esta es una string que contiene los caracteres recolectados por el automata hasta el momento
     * */
    private Token s54(String lexeme){
        Token token = null;
        char currentChar = file.charAt(currentPos);
        String currentLexeme;

        currentColumn += 1;

        if (currentChar == '\n'){
            currentLexeme = lexeme; // No leemos el \n para que no se rompa el formato
            currentPos += 1;
            // se hace currentLexeme.lenght() + 1 para tener en cuenta el \n
            token = new Token(SIMPLE_COMMENT, currentLexeme, currentRow,
                    startColumn(currentColumn, currentLexeme.length()+1));
            currentColumn = 0;
            currentRow += 1; // Se come al \n pero igual no son tan importante esos tokens
        }
        else {
            if (belongsToTheAlphabet(currentChar)) { // bucle
                currentLexeme = lexeme + Character.toString(currentChar);
                currentPos += 1;
                token = s54(currentLexeme);
            }
            else {
                // TODO: tirar error
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
        char[] caracteresEspeciales = {'\\', '?', '#', '$', '%', '&', '@', '¿', '¡', '!', 'ñ', '+', '-', '*', '/', '_', '>', '<', '"', '\'', '.', ';', ':', ' ', 'á', 'é', 'í', 'ó', 'ú', 'Á', 'É', 'Í', 'Ó', 'Ú', '(', ')', '[', ']', '{', '}', ','};
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
        char[] caracteresEspeciales = {'\\', '?', '#', '$', '%', '&', '@', '¿', '¡', '!', 'ñ', '+', '-', '*', '/', '_', '>', '<', '"', '\'', '.', ';', ':', ' ', 'á', 'é', 'í', 'ó', 'ú', 'Á', 'É', 'Í', 'Ó', 'Ú', '(', ')', '[', ']', '{', '}', ','};
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

    /**
     * Este metodo calcula en que columna empieza el token y la devuelve
     * @author Lucas Moyano
     * @param lastColumn la ultima columna del token
     * @param lexemeLength el numero de caracteres que tiene el lexema
     * */
    private int startColumn(int lastColumn, int lexemeLength){
        return lastColumn - (lexemeLength-1);
    }

    public String getEOF() {
        return EOF;
    }
}
