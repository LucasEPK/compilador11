package Exceptions;

import LexicalAnalyzer.Token;

/**
 * Clase que representa los errores de string con màs de 1024 caracteres
 * no es valido
 * @author Yeumen Silva
 */
public class LimitString  extends LexicalException{

    public LimitString(Token token){
        super(token);
    }

    @Override
    public String getExceptionType() {
        return "LIMITE DE 1024 CARACTERES ALCANZADO";
    }


}
