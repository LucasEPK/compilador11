package SemanticAnalyzer;

import LexicalAnalyzer.Token;

import java.lang.reflect.Method;
import java.util.*;

public class SymbolTable extends Commons {

    private Map<String,Struct> structs = new LinkedHashMap<>();;

    private Struct currentStruct;

    private Methods currentMethod;

    public SymbolTable(){
        addObject();
        addInt();
        addStr();
        addBool();
        addChar();
        addArray();
        addIO();
    }


    private void addInt(){
        Struct Int = new Struct("Int");
        this.structs.put("Int",Int);
    }

    private void addStr(){
        Struct Str = new Struct("Str");

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

        this.structs.put("Bool",Bool);
    }

    private void addChar(){
        Struct Char = new Struct("Char");

        this.structs.put("Char",Char);
    }

    private void addArray(){
        Struct Array = new Struct("Array");

        // fn length()->Int.
        Methods length = new Methods("length",false,this.structs.get("Int"),new LinkedHashMap<>(),0);

        Map<String,Methods> methods = new LinkedHashMap<>();

        methods.put("length",length);

        Array.setMethods(methods);

        this.structs.put("Array",Array);
    }

    private void addObject(){
        Struct Object = new Struct("Object");

        this.structs.put("Object",Object);
    }

    private void addIO(){
        Struct IO = new Struct("IO");

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
}
