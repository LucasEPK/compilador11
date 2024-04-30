package SemanticAnalyzer;

import Exceptions.SemanticExceptions.*;
import LexicalAnalyzer.Token;

import java.util.*;

/**
 * Clase representate de nuestra tabla de simbolos
 * en donde almacenamos toda la información
 * @author Yeumen Silva
 */

public class SymbolTable extends Commons {

    //Lista con todos los structs

    private Map<String,Struct> structs = new LinkedHashMap<>();;

    //Struct actual

    private Struct currentStruct;

    //Método actual

    private Methods currentMethod;

    //Start

    private  Methods start;

    /**
     * Método que devuelve el start
     * @return Method que representa el start
     * @author Yeumen Silva
     */

    public Methods getStart() {
        return start;
    }

    /**
     * Constructor de clase que inicia todas las
     * clases predefinidas
     * @author Yeumen Silva
     */

    public SymbolTable(){
        addObject();
        addInt();
        addStr();
        addBool();
        addChar();
        addArray();
        addIO();
    }

    /**
     * Método que agrega el start a la tabla de simbolos
     * @param token Token que contiene linea, columna y lexema
     * @author Yeumen Silva
     */

    public void addStart(Token token){
        Methods start = new Methods();
        start.setName(token.getLexeme());
        start.setToken(token);
        this.start = start;
        this.currentMethod = start;

    }

    /**
     * Método que agrega un struct a la tabla de simbolos,
     * Si el stuct ya existe, lo seteo como actual y sino
     * lo creo
     * @param token Token que contiene linea, columna y lexema
     * @author Yeumen Silva
     */

    public void addStructByStruct(Token token){

        String structName = token.getLexeme();

        //Verifico que la clase no exista ya en mi tabla

        if(this.structs.containsKey(structName)){
            this.currentStruct = this.structs.get(structName);
            //Si existe, verifico que no haya otro struct con ese nombre
            if(this.currentStruct.getHaveStruct()){
                //Lanzo error de que ya existe
                throw throwException("DuplicateStruct",token);
            }
            else {
                //Si ya existe, seteo que ya tiene struct
                this.currentStruct.setHaveStruct(true);
            }
        }
        else {
            //Si el struct no existe, lo debo agregar
            Struct newStruct = new Struct(structName);
            //Seteo que ya existe el struct
            newStruct.setHaveStruct(true);
            //Seteo que hereda de Object
            newStruct.setInheritFrom(this.structs.get("Object"));
            //Seteo su token
            newStruct.setToken(token);

            //Lo seteo como struct actual
            this.currentStruct = newStruct;
            //Lo agrego a lista de Strucrs
            this.structs.put(structName,newStruct);
        }

    }

    /**
     * Método que dado un impl, agrega ese struct a la tabla,
     * si ya existe lo seteo como actual, sino debo crearlo
     * @param token Token que contiene linea, columna y lexema
     * @author Lucas Moyano
     */


    public void addStructByImpl(Token token){
        String structName = token.getLexeme();

        if(this.structs.containsKey(structName)){
            //Seteo la clase com oactual
            this.currentStruct = this.structs.get(structName);
            //Si existe, verifico que no haya otro impl con ese nombre
            if(this.currentStruct.getHaveImpl()){
                //Lanzo error de que ya existe
                throw throwException("DuplicateImpl",token);
            }
            else {
                //Si ya existe, seteo que ya tiene impl
                this.currentStruct.setHaveImpl(true);
            }
        } else {
            //Si el struct no existe, lo debo agregar
            Struct newStruct = new Struct(structName);
            //Seteo que ya existe el impl
            newStruct.setHaveImpl(true);
            //Seteo que hereda de Object
            newStruct.setInheritFrom(this.structs.get("Object"));
            //Seteo su Token
            newStruct.setToken(token);

            //Lo seteo como struct actual
            this.currentStruct = newStruct;
            //Lo agrego a lista de Structs
            this.structs.put(structName,newStruct);
        }
    }

    /**
     * Método que añade un Método al struct actual
     * @param token Token que contiene linea, columna y lexema
     * @param isEstatic booleano si el metodo es estatico o no
     * @author Yeumen Silva
     */

    public void addMethodToStruct(Token token, boolean isEstatic){

        String methodName = token.getLexeme();

        //Verifico si el método ya existe
        if(currentStruct.getMethods().containsKey(methodName)){
            //Si existe deberia tirar
            throw throwException("DuplicateMethod",token);
        }
        //Defino el nuevo método con el nombre del ID
        Methods newMethod = new Methods(methodName);

        //Seteo su pos como el tamaño de la lista de métodos
        newMethod.setPos(currentStruct.getMethods().size());
        //Defino si es estático o no
        newMethod.setStatic(isEstatic);
        //Seteo su token
        newMethod.setToken(token);
        //Seteo este método como actual
        this.currentMethod = newMethod;
        //Agrego el método al struct actual
        this.currentStruct.getMethods().put(newMethod.getName(), newMethod);
    }

    /**
     * Función que añade a la tabla de simbolos un constructor al struct actual
     * @author Lucas Moyano
     * */
    public void addConstructorToStruct(Token token) {
        //Verifico si el constructor ya existe
        if(currentStruct.getConstructor() != null){
            //Si existe deberia tirar error
            throw throwException("DuplicateConstructor",token);
        }

        //Defino el nuevo constructor
        Methods newConstructor = new Methods(token.getLexeme());

        //Seteo su pos como el tamaño de la lista de métodos
        newConstructor.setPos(0);
        //Seteo su token
        newConstructor.setToken(token);
        //Seteo este método como actual
        this.currentMethod = newConstructor;
        // Agrego el constructor al struct actual
        this.currentStruct.setConstructor(newConstructor);
    }

    /**
     * Método que agrega herencia a un struct
     * @param token Token que contiene linea, columna y lexema
     * @author Yeumen Silva
     */

    public void addHeritance(Token token){
        String heritanceName = token.getLexeme();

        //Verifico que no sea Array,Bool,Char,Int,Str,IO
        if(Objects.equals(heritanceName,"Array" ) ||
                Objects.equals(heritanceName,"Bool" ) ||
                Objects.equals(heritanceName,"Char" ) ||
                Objects.equals(heritanceName,"Int" ) ||
                Objects.equals(heritanceName,"Str" ) ||
                Objects.equals(heritanceName,"IO")){
            throw throwException("InvalidHeritance",token);
        }

        //Verifico que la clase de la cual hereda no este definida en la tabla

        if(this.structs.containsKey(heritanceName)){
            //Si la clase ya estaba en la tabla
            Struct heritance = this.structs.get(heritanceName);
            this.currentStruct.setInheritFrom(heritance);
        } else {
            //La declaro como clase
            Struct newClass = new Struct(heritanceName);
            newClass.setToken(token);
            this.currentStruct.setInheritFrom(newClass);
        }

        //Seteo en ambos casos que la clase actual esta heredando
        this.currentStruct.setHaveInherit(true);

    }

    /**
     * Método que agrega tipo de return a un método
     * @param token Token que contiene linea, columna y lexema
     * @param type string con tipo que retorna
     * @param isArray booleano para saber si es o no un array
     * @author Lucas Moyano
     */

    public void addReturnToMethod(Token token, String type, boolean isArray){

        //Si retorna void lo agrego
        if(Objects.equals(type, "void")){
            Struct newVoid = new Struct("void");
            this.setToken(token);
            this.currentMethod.setGiveBack(newVoid);
        }
        //Como puede ser una referencia, debo verificar si existe el struct
        if(this.structs.containsKey(type)){
            //Si contiene la clave, solo seteo el return
            this.currentMethod.setGiveBack(this.structs.get(type));
        } else {

            //De otro modo, creo la nueva clase
            Struct newStruct = new Struct(type);
            //Seteo que hereda de Object
            newStruct.setInheritFrom(this.structs.get("Object"));
            newStruct.setToken(token);
            this.currentMethod.setGiveBack(newStruct);
        }

        // definimos si es un array o no
        this.currentMethod.setIsGiveBackArray(isArray);
    }

    /**
     * Agrega un atributo al currentStruct seteando el nombre, el tipo, la visibilidad y la posicion
     * @param token es un token del lexico
     * @param type un string con el tipo del atributo
     * @param isPublic un booleano que indica si el atributo es publico o no
     * @author Lucas Moyano
     * */
    public void addAttrToStruct(Token token, String type, boolean isPublic, boolean isArray){
        String attributeName = token.getLexeme();
        // Observa si ya existe un attribute con este nombre
        if (this.currentStruct.getAttributes().containsKey(attributeName)) { // Si existe tira error
            throw throwException("DuplicateAttribute", token);
        } else { // si no existe un attribute con ese mismo nombre entonces lo crea en el currentStruct
            // Chequea si existe el tipo en las clases de la tabla
            if (this.structs.containsKey(type)) {
                Struct structType = this.structs.get(type);
                int pos = structType.getAttributes().size();
                // si existe agregamos el nuevo atributo
                Attributes newAttribute = new Attributes(token.getLexeme(), structType, pos, isPublic, isArray);
                newAttribute.setToken(token);
                this.currentStruct.addAttribute(newAttribute.getName(), newAttribute);
            } else {
                // Si no existe debo agregarla
                // throw throwException("InvalidType", token);
                Struct newStruct = new Struct(type);
                int pos = newStruct.getAttributes().size();
                Attributes newAttribute = new Attributes(token.getLexeme(),newStruct,pos,isPublic,isArray);
                newAttribute.setToken(token);
                this.currentStruct.addAttribute(newAttribute.getName(),newAttribute);
            }
        }
    }

    /**
     * Agrega una variable a currentMethod seteando el nombre, el tipo, y la posicion
     * @param token es un token del lexico
     * @param type un string con el tipo de la variable
     * @author Lucas Moyano
     * */
    public void addVarToMethod(Token token, String type, boolean isArray){
        String varName = token.getLexeme();
        // Observa si ya existe una variable con este nombre
        if (this.currentMethod.getDefinedVar().containsKey(varName)) { // Si existe tira error
            throw throwException("DuplicateVariable", token);
        } else { // si no existe una variable con ese mismo nombre entonces la crea en el currentMethod
            // Chequea si existe el tipo en las clases de la tabla
            if (this.structs.containsKey(type)) {
                Struct structType = this.structs.get(type);
                structType.setToken(token);
                int pos = this.currentMethod.getDefinedVar().size();
                // si existe agregamos la nueva variable
                Variable newVariable = new Variable(token.getLexeme(), structType, pos, isArray);
                newVariable.setToken(token);
                this.currentMethod.addVariable(newVariable.getName(), newVariable);
            } else {
                // Si no existe debo agregarla
                // throw throwException("InvalidType", token);
                Struct newStruct = new Struct(type);
                newStruct.setToken(token);
                int pos = this.currentMethod.getDefinedVar().size();
                Variable newVariable = new Variable(token.getLexeme(),newStruct,pos,isArray);
                newVariable.setToken(token);
                this.currentMethod.addVariable(newVariable.getName(),newVariable);
            }
        }
    }

    /**
     * Agrega una parametro a currentMethod seteando el nombre, el tipo, y la posicion
     * @param token es un token del lexico
     * @param type un string con el tipo de la variable
     * @author Lucas Moyano
     * */
    public void addParameterToMethod(Token token, String type, boolean isArray) {
        String parName = token.getLexeme();
        // Observa si ya existe un parametro con este nombre
        if (this.currentMethod.getParamsOfMethod().containsKey(parName)) { // Si existe tira error
            throw throwException("DuplicateParameter", token);
        } else { // si no existe un parametro con ese mismo nombre entonces se crea en el currentMethod
            // Chequea si existe el tipo en las clases de la tabla
            if (this.structs.containsKey(type)) {
                Struct structType = this.structs.get(type);
                structType.setToken(token);
                int pos = this.currentMethod.getParamsOfMethod().size();
                // si existe agregamos el nuevo parametro
                Variable newParameter = new Variable(token.getLexeme(), structType, pos, isArray);
                newParameter.setToken(token);
                this.currentMethod.addParameter(newParameter.getName(), newParameter);
            } else {
                // Si no existe debo agregarla
                // throw throwException("InvalidType", token);
                Struct newStruct = new Struct(type);
                newStruct.setToken(token);
                newStruct.setToken(token);
                int pos = this.currentMethod.getParamsOfMethod().size();
                Variable newParameter = new Variable(token.getLexeme(),newStruct,pos,isArray);
                newParameter.setToken(token);
                this.currentMethod.addParameter(newParameter.getName(),newParameter);
            }
        }
    }

    /**
     * Agrega clase predfinida Int a la tabla
     * @author Yeumen Silva
     */

    private void addInt(){
        Struct Int = new Struct("Int");
        //Agrego que Int hereda de Object
        Int.setInheritFrom(this.structs.get("Object"));
        Int.setHaveStruct(true);
        Int.setHaveImpl(true);

        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        Int.setConstructor(constructor);

        this.structs.put("Int",Int);
    }

    /**
     * Agrega clase predfinida Str a la tabla,
     * tambien agrega sus métodos
     * @author Yeumen Silva
     */

    private void addStr(){
        Struct Str = new Struct("Str");

        //Agrego que Str hereda de Object
        Str.setInheritFrom(this.structs.get("Object"));
        Str.setHaveStruct(true);
        Str.setHaveImpl(true);

        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        Str.setConstructor(constructor);

        //fn length()->Int
        Methods length = new Methods("length",false,this.structs.get("Int"), new LinkedHashMap<String,Variable>(),0);

        //fn concat(Str s)->Str
        Variable s = new Variable("s",Str,0, false);
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

    /**
     * Agrega clase predfinida Bool a la tabla,
     * @author Yeumen Silva
     */

    private void addBool(){
        Struct Bool = new Struct("Bool");

        //Agrego que Bool hereda de Object
        Bool.setInheritFrom(this.structs.get("Object"));
        Bool.setHaveStruct(true);
        Bool.setHaveImpl(true);

        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        Bool.setConstructor(constructor);

        this.structs.put("Bool",Bool);
    }

    /**
     * Agrega clase predfinida Char a la tabla,
     * @author Yeumen Silva
     */

    private void addChar(){
        Struct Char = new Struct("Char");

        //Agrego que Char hereda de Object
        Char.setInheritFrom(this.structs.get("Object"));
        Char.setHaveStruct(true);
        Char.setHaveImpl(true);

        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        Char.setConstructor(constructor);

        this.structs.put("Char",Char);
    }

    /**
     * Agrega clase predfinida Array a la tabla,
     * tambien agrega sus métodos
     * @author Yeumen Silva
     */

    private void addArray(){
        Struct Array = new Struct("Array");

        //Agrego que Array hereda de Object
        Array.setInheritFrom(this.structs.get("Object"));
        Array.setHaveStruct(true);
        Array.setHaveImpl(true);

        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        Array.setConstructor(constructor);

        // fn length()->Int.
        Methods length = new Methods("length",false,this.structs.get("Int"),new LinkedHashMap<>(),0);

        Map<String,Methods> methods = new LinkedHashMap<>();

        methods.put("length",length);

        Array.setMethods(methods);

        this.structs.put("Array",Array);
    }

    /**
     * Agrega clase predfinida Object a la tabla,
     * @author Yeumen Silva
     */

    private void addObject(){
        Struct Object = new Struct("Object");
        Object.setHaveStruct(true);
        Object.setHaveImpl(true);
        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        Object.setConstructor(constructor);
        this.structs.put("Object",Object);
    }

    /**
     * Agrega clase predfinida IO a la tabla,
     * tambien agrega sus métodos
     * @author Yeumen Silva
     */

    private void addIO(){
        Struct IO = new Struct("IO");

        //Agrego que IO hereda de Object
        IO.setInheritFrom(this.structs.get("Object"));
        IO.setHaveStruct(true);
        IO.setHaveImpl(true);

        //Creo constructor
        Methods constructor = new Methods("constructor",new LinkedHashMap<>());
        //Lo agrego
        IO.setConstructor(constructor);

        // st fn out_str(Str s)->void
        Variable s = new Variable("s",this.structs.get("Str"),0, false);
        Map<String,Variable> hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("s",s);
        Methods out_str = new Methods("out_str",true,new Struct("void"),hashMapAtributes,0 );

        // st fn out_int(Int i)->void
        Variable i = new Variable("i",this.structs.get("Int"),0, false);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("i",i);
        Methods out_int = new Methods("out_int",true,new Struct("void"),hashMapAtributes,1);

        // st fn out_bool(Bool b)->void
        Variable b = new Variable("b",this.structs.get("Bool"),0, false);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("b",b);
        Methods out_bool = new Methods("out_bool",true,new Struct("void"),hashMapAtributes,2);

        // st fn out_char(Char c)->void
        Variable c = new Variable("c",this.structs.get("Char"),0, false);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("c",c);
        Methods out_char = new Methods("out_char",true,new Struct("void"),hashMapAtributes,3);

        // st fn out_array_int(Array a)->void
        Variable a = new Variable("a",this.structs.get("Int"),0, true);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("a",a);
        Methods out_array_int = new Methods("out_array_int",true,new Struct("void"),hashMapAtributes,4);

        // st fn out_array_str(Array a)->void
        a = new Variable("a", this.structs.get("Str"),0, true);
        a.setIsArray(true);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("a",a);
        Methods out_array_str = new Methods("out_array_str",true,new Struct("void"),hashMapAtributes,5);

        // st fn out_array_bool(Array a)->void
        a = new Variable("a", this.structs.get("Bool"),0, true);
        a.setIsArray(true);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("a",a);
        Methods out_array_bool = new Methods("out_array_bool",true,new Struct("void"),hashMapAtributes,6);

        // st fn out_array_char(Array a)->void
        a = new Variable("a", this.structs.get("Char"),0, true);
        a.setIsArray(true);
        hashMapAtributes = new LinkedHashMap<>();
        hashMapAtributes.put("a",a);
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

    /**
     * Método que se encarga de hacer la consolidación de la tabla,
     * chequeando herencia de atributos y métodos, herencia ciclica,
     * ,tipos no definidos y structs que no poseen impl o struct
     * @author Yeumen Silva
     */

    public void consolidate(){
        Map<String,Struct> structs = this.structs;
        for (String structName : structs.keySet()){
            Struct actualStruct = structs.get(structName);
            /*Debo verificar que que la Struct tenga seteados su haveImpl
            y su haveStruct como true ya que de otro modo significa que por ej
            se heredo pero nunca se declaro
             */
            if(actualStruct.getHaveStruct() == false){
                //Lanzo error de que la clase no está definida
                throw throwException("UndefinedStruct",actualStruct.getToken());
            }
            if(actualStruct.getHaveImpl() == false){
                //Lanzo error de que el impl no está definido
                throw throwException("UndefinedImpl",actualStruct.getToken());
            }

            /*
            Verifico que ningun tipo de algun atributo,
             variable o parametro de las structs no este declarado
             */

            verificateUndeclaredTypes();

            /*
            Hago lo mismo pero para start
             */
            for(Variable actualVariable : this.start.getDefinedVar().values()){
                if(this.structs.containsKey(actualVariable.getType().getName()) == false){
                    throw throwException("InvalidType", actualVariable.getToken());
                }
            }


            //Verifico atributos y métodos de ancestros siempre y cuando no herede de Object
            if(actualStruct.getInheritFrom() != this.structs.get("Object")){
                //Si hereda de una clase distinta de Object, debo buscar ciclos
                if(haveCycles(actualStruct,actualStruct)){
                    //Lanzo error
                    throw  throwException("HeritanceCycle", actualStruct.getToken());
                }
            }
            //Agrego herencia de atributos
            LinkedHashMap<String,Attributes> heritanceAttributes = findAncestralAtributtes(actualStruct, new LinkedHashMap<String,Attributes>());
            actualStruct.setAttributes(heritanceAttributes);

            // Agrego herencia de métodos
            LinkedHashMap<String,Methods> heritanceMethdos = findAncestralMethods(actualStruct, new LinkedHashMap<String,Methods>());
            actualStruct.setMethods(heritanceMethdos);

            actualStruct.setConsolidate(true);

        }

    }

    /**
     * Método encargado de verificar que ningun tipo delcarado en el código
     * no este definido
     * @author Yeumen Silva
     */

    private void verificateUndeclaredTypes(){
        //Recorro todas las structs
        for(Struct actualStruct : this.structs.values()){
                //Recorro sus atributos
                for (Attributes actualAttribute : actualStruct.getAttributes().values()) {
                    //Verifico que el tipo declarado en el atributo este definido
                    if (this.structs.containsKey(actualAttribute.getType().getName()) == false) {
                        throw throwException("InvalidType", actualAttribute.getToken());
                    }
                }
                //Recorro sus métodos
                for (Methods actualMethod : actualStruct.getMethods().values()) {

                    //Verifico que los tipos de return esten definidos
                    if (Objects.equals(actualMethod.getGiveBack().getName(), "void") == false) {
                        if (this.structs.containsKey(actualMethod.getGiveBack().getName() ) == false) {

                            throw throwException("InvalidType", actualMethod.getToken());
                        }
                    }
                    //Recorro los parametros de los métodos
                    for (Variable actualVariable : actualMethod.getParamsOfMethod().values()) {
                        //Verifico que los tipos esten definidos
                        if (this.structs.containsKey(actualVariable.getType().getName()) == false) {
                            throw throwException("InvalidType", actualVariable.getToken());
                        }
                    }
                    //Recorro las variables declaradas dentro de cada metodo
                    for (Variable actualVariable : actualMethod.getDefinedVar().values()) {
                        //Verifico que los tipos esten definidos
                        if (this.structs.containsKey(actualVariable.getType().getName()) == false) {
                            throw throwException("InvalidType", actualVariable.getToken());
                        }
                    }
                }

                /*Verifico que el constructor no reciba un parametro
                con un tipo inexistente
                 */
            for(Variable actualVariable : actualStruct.getConstructor().getParamsOfMethod().values()){
                if(this.structs.containsKey(actualVariable.getType().getName()) == false){
                    throw throwException("InvalidType", actualVariable.getToken());
                }
            }
            /*
            Verifico que no se declare una variable que tenga
            tipo inexistente
             */
            for(Variable actualVariable : actualStruct.getConstructor().getDefinedVar().values()){
                if(this.structs.containsKey(actualVariable.getType().getName()) == false){
                    throw throwException("InvalidType", actualVariable.getToken());
                }
            }
        }

    }

    /**
     * Método encargado de verificar que no produzca herencia ciclica
     * @param initialStruct Struct la cual quiero verificar que no tenga ciclos
     * @param actualStruct Ancestro actual
     * @return boolean, true si hay ciclos, false en otro caso
     */
    private boolean haveCycles(Struct initialStruct, Struct actualStruct){
        boolean haveCycle = false;

        //Verifico que algun Struct de dentro no esté definido
        if(actualStruct.getHaveStruct() == false){
            //Lanzo error de que la clase no está definida
            throw throwException("UndefinedStruct",actualStruct.getToken());
        }
        if(actualStruct.getHaveImpl() == false){
            //Lanzo error de que el impl no está definido
            throw throwException("UndefinedImpl",actualStruct.getToken());
        }

        if(actualStruct.getInheritFrom() == initialStruct){
            //Quiere decir que hay un ciclo
            haveCycle = true;
        }
        else {
            //Sigo recorriendo mientras la herencia no sea Object
            if(actualStruct.getInheritFrom() != this.structs.get("Object")){
                //Como Object hereda de null
                if(actualStruct.getInheritFrom() != null) {
                    if (actualStruct.getInheritFrom().getInheritFrom() == null) {
                        Struct newActualStruct = this.structs.get(actualStruct.getInheritFrom().getName());
                        if (newActualStruct != null) {
                            actualStruct.setInheritFrom(newActualStruct);
                        }
                    }
                    haveCycle = haveCycles(initialStruct, actualStruct.getInheritFrom());
                }
            }

        }
        return haveCycle;
    }

    /**
     * Método que se encarga de agregar a una clase todos los atributos
     * de sus clases ancestro, verificando que no hayan errores en estos
     * y corrigiendo su posición
     * @param children Struct actual
     * @param attributesList Lista donde voy a almacenar los atributos
     * @return Lista con todos los atriutos heredads
     * @author Yeumen silva
     */

    private LinkedHashMap<String,Attributes> findAncestralAtributtes(Struct children, LinkedHashMap<String,Attributes> attributesList){
        if(children.getInheritFrom() != null) {
            if (children.getInheritFrom() != this.structs.get("Object")) {
                //Si esta clase todavia no ha heredado
                if (!children.getIsConsolidate()) {
                    attributesList = findAncestralAtributtes(children.getInheritFrom(), attributesList);
                }
            }
            //Voy a almacenar todos los atributos actualizando su pos
            for (Attributes attribute : children.getAttributes().values()) {
                //Verifico que el atributo no este declarado
                if (attributesList.get(attribute.getName()) == null) {
                    if (attributesList.isEmpty()) {
                        //Seteo booleano de que es heredado
                        attribute.setInherited(true);
                        attributesList.put(attribute.getName(), attribute);
                    } else {
                        //Seteo su nueva pos
                        attribute.setPos(attributesList.size());
                        //Seteo booleano de que es heredado
                        attribute.setInherited(true);
                        attributesList.put(attribute.getName(), attribute);
                    }
                } else {
                    throw throwException("DuplicateAttributeHeritance", attribute.getToken());
                }

            }
        }
        return attributesList;
    }

    /**
     * Método que se encarga de agregar a una clase todos los métodos
     * de sus clases ancestro, verificando que no hayan errores en estos
     * y corrigiendo su posición
     * @param children Struct actual
     * @param methodsList Lista donde voy a almacenar los métodos
     * @return Lista con todos le métodos heredados
     * @author Yeumen Silva
     */

    private LinkedHashMap<String,Methods> findAncestralMethods(Struct children, LinkedHashMap<String,Methods> methodsList){
        if(children.getInheritFrom() != null) {
            if (children.getInheritFrom() != this.structs.get("Object")) {
                methodsList = findAncestralMethods(children.getInheritFrom(), methodsList);
            }
            //Si la clase no tiene constructor, lo hereda
            if (children.getConstructor() == null) {
                throw throwException("UndefinedConstructor", children.getToken());
            }
            //Guardo métodos y actualizo su pos
            for (Methods method : children.getMethods().values()) {
                Methods ancestralMethodEquals = methodsList.get(method.getName());
                //Si existe un método ya declarado en la lista con mismo nombre
                if (ancestralMethodEquals == null) {
                    method.setInherited(true);
                    method.setPos(methodsList.size());
                    methodsList.put(method.getName(), method);
                } else {
                    //Verifico que la sobreescritura sea correcta
                    //Verifico que tenga la misma cantidad
                    if (ancestralMethodEquals.getParamsOfMethod().size() != method.getParamsOfMethod().size()) {
                        throw throwException("InvalidOverrideLength", method.token);
                    }
                    //Verifico el tipo de parámetros
                    boolean equals = compareMethods(method.getParamsOfMethod(), ancestralMethodEquals.getParamsOfMethod());
                    if (equals == false) {
                        throw throwException("InvalidOverrideType", method.token);
                    }
                    //Verifico el tipo de return
                    if (Objects.equals(method.getGiveBack().getName(), ancestralMethodEquals.getGiveBack().getName()) == false
                    || method.getIsGiveBackArray() != ancestralMethodEquals.getIsGiveBackArray()) {
                        throw throwException("InvalidOverrideReturn", method.token);
                    }
                    if (Objects.equals(method.getIsStatic(), ancestralMethodEquals.getIsStatic()) == false) {
                        throw throwException("InvalidOverrideStatic", method.token);
                    }
                    method.setPos(methodsList.size());
                    method.setInherited(true);
                    methodsList.replace(method.getName(), ancestralMethodEquals, method);
                }

            }
        }
        return methodsList;
    }

    /**
     * Método que se encarga de verificar que los tipos de una
     * sobreescritura sean correcots
     * @param method Método redefinido
     * @param ancestralMethod Método de clase ancestro
     * @return booleano, true si son iguales, false si son distintos
     * @author Yeumen Silva
     */

    private boolean compareMethods(Map<String,Variable> method, Map<String,Variable> ancestralMethod){
        boolean equals = true;
        //Creo dos arreglos para almacenar los valores de los tipos
        Struct[] methodTypes = new Struct[method.size()];
        Boolean[] parametterIsArray = new Boolean[method.size()];
        Struct[] methodAncestralTypes = new Struct[ancestralMethod.size()];
        Boolean[] ancestralParametterIsArray = new Boolean[ancestralMethod.size()];

        //Recorro ambas listas para almacenar los tipos
        int i = 0;
        for(Variable variable : method.values()){
            methodTypes[i] = variable.getType();
            parametterIsArray[i] = variable.getIsArray();
            i++;
        }
        i = 0;
        for (Variable variable : ancestralMethod.values()){
            methodAncestralTypes[i] = variable.getType();
            ancestralParametterIsArray[i] = variable.getIsArray();
            i++;
        }
        //Comparo los tipos
        for(i = 0; i < methodTypes.length;i++){
            //Si el tipo de algun parametro es distinto, esta mal sobrescrito
            if(Objects.equals(methodTypes[i],methodAncestralTypes[i]) == false || parametterIsArray[i] != ancestralParametterIsArray[i]){
                equals = false;
            }
        }

        return equals;


    }

    /**
     * Método que devuelve la lista de strcuts declarados
     * @return Lista de structs
     * @Author Yeumen Silva
     */


    public      Map<String, Struct> getStructs() {
        return structs;
    }

    /**
     * Método encargado de asignar los diferentes tipos de errores
     * @param type String con el tipo de error
     * @param token es un token del lexico
     * @return Exepcion de tipo semántico
     * @author Lucas Moyano, Yeumen Silva
     */

    private SemanticException throwException(String type, Token token){

        SemanticException semanticException = null;

        switch (type){
            case ("DuplicateStruct"):
                semanticException = new DuplicateStruct(token);
                break;
            case ("DuplicateImpl"):
                semanticException = new DuplicateImpl(token);
                break;
            case ("DuplicateMethod"):
                semanticException = new DuplicateMethod(token);
                break;
            case ("InvalidHeritance"):
                semanticException = new InvalidHeritance(token);
                break;
            case ("DuplicateAttribute"):
                semanticException = new DuplicateAttribute(token);
                break;
            case ("InvalidType"):
                semanticException = new InvalidType(token);
                break;
            case("DuplicateVariable") :
                semanticException = new DuplicateVariable(token);
                break;
            case ("DuplicateParameter") :
                semanticException = new DuplicateParameter(token);
                break;
            case ("UndefinedStruct"):
                semanticException = new UndefinedStruct(token);
                break;
            case ("HeritanceCycle"):
                semanticException = new HeritanceCycle(token);
                break;
            case ("DuplicateAttributeHeritance"):
                semanticException = new DuplicateAttributeHeritance(token);
                break;
            case ("InvalidOverrideLength"):
                semanticException = new InvalidOverrideLength(token);
                break;
            case ("InvalidOverrideType"):
                semanticException = new InvalidOverrideType(token);
                break;
            case ("InvalidOverrideReturn"):
                semanticException = new InvalidOverrideReturn(token);
                break;
            case("UndefinedImpl"):
                semanticException = new UndefinedImpl(token);
                break;
            case("DuplicateConstructor"):
                semanticException = new DuplicateConstructor(token);
                break;
            case ("InvalidOverrideStatic"):
                semanticException = new InvalidOverrideStatic(token);
                break;
            case ("UndefinedConstructor"):
                semanticException = new UndefinedConstructor(token);
                break;
        }

        return semanticException;

    }


}
