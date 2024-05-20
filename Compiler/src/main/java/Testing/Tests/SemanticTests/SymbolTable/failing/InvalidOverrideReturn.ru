/? Descripción: caso de prueba que verifica que si hay sobreescritura
/? el retorno sea el mismo
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 18 | COLUMNA 4 | SOBRESCRITURA INCORRECTA: TIPO DE RET DISTINTO

struct A{}

impl A{
.(){}
fn m1() -> void{} }

struct B : A{}

impl B{
.(){}
fn m1() -> Int{}}

start{}
