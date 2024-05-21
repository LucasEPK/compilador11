/? Descripción: caso de prueba que verifica que no se acceda
/? a un atributo privado de una clase dentro de una subclase
/? Salida esperada:
/? ERROR: SEMÁNTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 17 | COLUMNA 1 | SE ESTA INTENTANDO ACCEDER A UNA VARIABLE HERADADA PRIVADA


struct C{
pri Int a;}
impl C{
.(){}}

struct A : C{}
impl A{
.(){}
fn m1() -> void{
a = 2;}}

start{}