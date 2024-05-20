/? Descripción: caso de prueba que verifica que no hayan ciclos de herencia
/? Salida esperada:
/? ERROR: SEMÁNTICO - DECLARACIONES
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 7 | COLUMNA 8 | SE PRODUJO UN CICLO EN LA HERENCIA

struct Pepe : Juan {}

impl Pepe{
.(){}}

struct Juan : Alberto{}

impl Juan{
.(){}}

struct Alberto : Pepe{}

impl Alberto{
.(){}}

start{}