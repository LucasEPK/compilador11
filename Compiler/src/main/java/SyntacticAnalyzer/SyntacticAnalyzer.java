package SyntacticAnalyzer;

import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SyntacticAnalyzer {

    private Token actualToken;
    private SyntacticExecutor syntacticExecutor;

    public SyntacticAnalyzer(String inputPath, String outputPath){

        this.syntacticExecutor = new SyntacticExecutor(inputPath,outputPath);

        // TODO aca empieza el primer llamado a program

        program();


    }

    private void match(String actualname){

        if(Objects.equals(this.actualToken.getLexeme(), actualname)){

            this.actualToken = syntacticExecutor.getNextToken();

        }
        else {
            //error
        }
    }

    private boolean verifyEquals(List<String> listStirngs){

        String actualLexeme = this.actualToken.getLexeme();

        for (String lexeme : listStirngs){
            if(Objects.equals(actualLexeme,lexeme)){
                return true;
            }
        }
        return false;
    }

    private void program() {

        List<String> firstDefinitionList = new ArrayList<>((Arrays.asList("impl", "struct")));
        List<String> firstStartList = new ArrayList<>((Arrays.asList("impl", "struct")));

        if (verifyEquals(firstStartList)) {
            // start();
        } else {
            if (verifyEquals(firstDefinitionList)) {
                // listaDefiniciones()
                // start()
            }
        }

    }
}
