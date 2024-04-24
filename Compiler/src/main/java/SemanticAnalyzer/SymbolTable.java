package SemanticAnalyzer;

import Exceptions.SemanticExceptions.DuplicateImpl;
import Exceptions.SemanticExceptions.DuplicateStruct;
import Exceptions.SemanticExceptions.SemanticException;
import LexicalAnalyzer.Token;

import java.lang.reflect.Method;
import java.util.*;

public class SymbolTable extends Commons {

    //Lista con todos los structs

    private Map<String,Struct> structs = new LinkedHashMap<>();;

    //Struct actual

    private Struct currentStruct;

    //Método actual

    private Methods currentMethod;

    //Start

    private  Methods start;



    public SymbolTable(){
        addObject();
        addInt();
        addStr();
        addBool();
        addChar();
        addArray();
        addIO();
    }

    public void addStart(Token token){
        Methods start = new Methods();
        start.setName(token.getLexeme());
        start.setToken(token);
        this.start = start;
        this.currentMethod = start;

    }

    public void addStructbyStruct(Token token){

        String structName = token.getLexeme();

        //Verifico que la clase no exista ya en mi tabla

        if(this.structs.containsKey(structName)){
            this.currentStruct = this.structs.get(structName);
            //Si existe, verifico que no haya otro struct con ese nombre
            if(this.currentStruct.getHaveStruct()){
                //Lanzo error de que ya existe
                throw throwException("DuplicateStruct",token);
            }
        }
        else {
            //Si el struct no existe, lo debo agregar
            Struct newStruct = new Struct(structName);
            //Seteo que ya existe el struct
            newStruct.setHaveStruct(true);

            //Lo seteo como struct actual
            this.currentStruct = newStruct;
            //Lo agrego a lista de Strucrs
            this.structs.put(structName,newStruct);
        }

    }

    public void addStructByImpl(Token token){
        String structName = token.getLexeme();

        if(this.structs.containsKey(structName)){
            this.currentStruct = this.structs.get(structName);
            //Si existe, verifico que no haya otro impl con ese nombre
            if(this.currentStruct.getHaveImpl()){
                //Lanzo error de que ya existe
                throw throwException("DuplicateImpl",token);
            }
        } else {
            //Si el struct no existe, lo debo agregar
            Struct newStruct = new Struct(structName);
            //Seteo que ya existe el impl
            newStruct.setHaveImpl(true);

            //Lo seteo como struct actual
            this.currentStruct = newStruct;
            //Lo agrego a lista de Structs
            this.structs.put(structName,newStruct);
        }
    }
    
    private void addInt(){
        Struct Int = new Struct("Int");
        //Agrego que Int hereda de Object
        Int.setInheritFrom(this.structs.get("Object"));

        this.structs.put("Int",Int);
    }

    private void addStr(){
        Struct Str = new Struct("Str");

        //Agrego que Str hereda de Object
        Str.setInheritFrom(this.structs.get("Object"));

        //fn length()->Int
        Methods length = new Methods("length",false,this.structs.get("Int"), new LinkedHashMap<String,Variable>(),0);

        //fn concat(Str s)->Str
        Variable s = new Variable("s",Str,0);
        Map<String, Variable> hashMapS = new LinkedHashMap<>();
        hashMapS.put("s", s);

        Methods concat = new Methods("concat",false,Str,hashMapS ,1);

        //Agrego métodos a struct Str

        Map<String,Methods> methods = new LinkedHashMap<>();
        methods.put("length",length);
        methods.put("concat",concat);

        Str.setMethods(methods);

        this.structs.put("Str",Str);
    }

    private void addBool(){
        Struct Bool = new Struct("Bool");

        //Agrego que Bool hereda de Object
        Bool.setInheritFrom(this.structs.get("Object"));

        this.structs.put("Bool",Bool);
    }

    private void addChar(){
        Struct Char = new Struct("Char");

        //Agrego que Char hereda de Object
        Char.setInheritFrom(this.structs.get("Object"));

        this.structs.put("Char",Char);
    }

    private void addArray(){
        Struct Array = new Struct("Array");

        //Agrego que Array hereda de Object
        Array.setInheritFrom(this.structs.get("Object"));

        // fn length()->Int.
        Methods length = new Methods("length",false,this.structs.get("Int"),new LinkedHashMap<>(),0);

        Map<String,Methods> methods = new LinkedHashMap<>();

        methods.put("length",length);

        Array.setMethods(methods);

        this.structs.put("Array",Array);
    }

    private void addObject(){
        Struct Object = new Struct("Object");
        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        Object.setConstructor(constructor);
        this.structs.put("Object",Object);
    }

    private void addIO(){
        Struct IO = new Struct("IO");

        //Agrego que IO hereda de Object
        IO.setInheritFrom(this.structs.get("Object"));

        // st fn out_str(Str s)->void
        Variable s = new Variable("s",this.structs.get("Str"),0);
        Map<String,Variable> hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("s",s);
        Methods out_str = new Methods("out_str",true,new Struct("void"),hashMapAtributes,0 );

        // st fn out_int(Int i)->void
        Variable i = new Variable("i",this.structs.get("Int"),0);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("i",i);
        Methods out_int = new Methods("out_int",true,new Struct("void"),hashMapAtributes,1);

        // st fn out_bool(Bool b)->void
        Variable b = new Variable("b",this.structs.get("Bool"),0);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("b",b);
        Methods out_bool = new Methods("out_bool",true,new Struct("void"),hashMapAtributes,2);

        // st fn out_char(Char c)->void
        Variable c = new Variable("c",this.structs.get("Char"),0);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("c",c);
        Methods out_char = new Methods("out_char",true,new Struct("void"),hashMapAtributes,3);

        // st fn out_array_int(Array a)->void
        Variable a = new Variable("a",this.structs.get("Array"),0);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("a",a);
        Methods out_array_int = new Methods("out_array_int",true,new Struct("void"),hashMapAtributes,4);

        // st fn out_array_str(Array a)->void
        Methods out_array_str = new Methods("out_array_str",true,new Struct("void"),hashMapAtributes,5);

        // st fn out_array_bool(Array a)->void
        Methods out_array_bool = new Methods("out_array_bool",true,new Struct("void"),hashMapAtributes,6);

        // st fn out_array_char(Array a)->void
        Methods out_array_char = new Methods("out_array_char", true,new Struct("void"),hashMapAtributes,7);

        // st fn in_str()->Str
        hashMapAtributes = new LinkedHashMap<>();
        Methods in_str = new Methods("in_str",true,this.structs.get("Str"),hashMapAtributes,8);

        // st fn in_int()->Int
        Methods in_int = new Methods("in_int",true,this.structs.get("Int"),hashMapAtributes,9);

        // st fn in_bool()->Bool
        Methods in_bool = new Methods("in_bool",true,this.structs.get("Bool"),hashMapAtributes,10);

        // st fn in_char()->Char
        Methods in_char = new Methods("in_char",true,this.structs.get("Char"),hashMapAtributes,11);

        HashMap<String, Methods> methods = new LinkedHashMap<>();

        methods.put("out_str",out_str);
        methods.put("out_int",out_int);
        methods.put("out_bool",out_bool);
        methods.put("out_char",out_char);
        methods.put("out_array_int",out_array_int);
        methods.put("out_array_bool",out_array_bool);
        methods.put("out_array_str",out_array_str);
        methods.put("out_array_char",out_array_char);
        methods.put("in_str",in_str);
        methods.put("in_int",in_int);
        methods.put("in_bool",in_bool);
        methods.put("in_char",in_char);

        IO.setMethods(methods);
        this.structs.put("IO",IO);

    }


    public      Map<String, Struct> getStructs() {
        return structs;
    }

    private SemanticException throwException(String type, Token token){

        SemanticException semanticException = null;

        switch (type){
            case ("DuplicateStruct"):
                semanticException = new DuplicateStruct(token);
                break;
            case ("DuplicateImpl") :
                semanticException = new DuplicateImpl(token);
                break;
        }

        return semanticException;

    }


}
