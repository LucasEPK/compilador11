/? Descripción: caso de prueba que verifica que si hay sobreescritura
/? pero un método es estatico y el otro no
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 16 | COLUMNA 7 | SOBRESCRITURA INCORRECTA: DECLARACION ESTATICA DIFERENTE

struct A{}
impl A{
.(){}
st fn m1() -> void{}}

struct B : A{}
impl B{
.(){}
fn m1() -> void{}}

start{}