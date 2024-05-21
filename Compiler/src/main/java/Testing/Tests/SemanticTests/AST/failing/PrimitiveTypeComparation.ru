/? Descripción: caso de prueba que verifica que si se comparan dos tipos primitivos, estos sean iguales
/? Salida esperada:
/? ERROR: SEMANTICO - SENTENCIAS
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 15 | COLUMNA 7 | NO SE PUEDEN COMPARAR TIPOS PRIMITIVOS DIFERENTES

struct A{}
impl A{
.(){}}

start{
Bool b;
A v1;
b = v1 == v1;
b = 1 == true;}
