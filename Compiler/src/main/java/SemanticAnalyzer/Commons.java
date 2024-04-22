package SemanticAnalyzer;

import LexicalAnalyzer.Token;

public abstract class Commons {

    protected String name;

    protected Token token;

    protected String toJson(int tabs){
        return null;
    }

    protected String addtabs(int tab){
        String stringWithTabs = "";
        for (int i = 0; i < tab; i++ ){
            stringWithTabs = stringWithTabs + "\t";
        }
        return stringWithTabs;
    }
}
