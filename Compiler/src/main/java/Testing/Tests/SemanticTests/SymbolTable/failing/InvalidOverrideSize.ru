/? Descripción: caso de prueba que verifica que si hay sobreescritura
/? la cantidad de parametros sea la misma
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 18 | COLUMNA 4 | SOBRESCRITURA INCORRECTA: NUMERO DE PARAMETROS DISTINTO

struct A{}

impl A{
.(){}
fn m1(Int a) -> void{} }

struct B : A{}

impl B{
.(){}
fn m1(Int b, Char a) -> void{}}

start{}