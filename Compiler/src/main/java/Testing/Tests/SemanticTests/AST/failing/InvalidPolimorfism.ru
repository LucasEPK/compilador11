/? Descripción: caso de prueba que verifica que si B hereda de A
/? y se declara variable de tipo B, no se puede asignar un objeto de tipo A
/? Salida esperada:
/? ERROR: SEMÁNTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 17 | COLUMNA 1 | TIPOS DISTINTOS

struct A{}
impl A{
.(){}}

struct B : A{}
impl B{
.(){}
fn m1(A p1)->void{
B v1;
v1 = new A();}}

start{
Int a;
}