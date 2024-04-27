/? Descripción: caso de prueba que verifica que no existan dos atributos iguales
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 7 | COLUMNA 6 | EL ATRIBUTO a YA ESTA DECLARADO EN EL ALCANCE

struct A{
Int a;
Bool a;}
impl A{
.(){}}
start{}