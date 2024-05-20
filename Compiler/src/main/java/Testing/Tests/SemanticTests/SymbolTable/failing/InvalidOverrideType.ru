/? Descripción: caso de prueba que verifica que si hay sobreescritura
/? el tipo de los parametros sea el mismo
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 17 | COLUMNA 4 | SOBRESCRITURA INCORRECTA: TIPO DE PARAMETRO DISTINTO

struct A{}

impl A{
.(){}
fn m1(Int a) -> void{} }

struct B : A{}

impl B{
.(){}
fn m1(Char b) -> void{}}

start{}