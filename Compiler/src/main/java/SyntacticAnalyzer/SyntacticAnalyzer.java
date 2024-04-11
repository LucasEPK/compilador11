package SyntacticAnalyzer;

import LexicalAnalyzer.LexicalAnalyzer;
import LexicalAnalyzer.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SyntacticAnalyzer {

    private Token actualToken;
    private SyntacticExecutor syntacticExecutor;

    public SyntacticAnalyzer(String inputPath, String outputPath){

        this.syntacticExecutor = new SyntacticExecutor(inputPath,outputPath);

        // TODO aca empieza el primer llamado a program


    }

}
