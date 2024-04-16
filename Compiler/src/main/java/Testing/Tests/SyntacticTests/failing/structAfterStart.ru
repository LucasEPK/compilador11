/? Descripción: este caso de prueba se fija si el lenguaje tira error cuando
/? tiene un struct después de un start
/? Salida esperada:
/? ERROR: SINTÁCTICO
/? | NUMERO DE LINEA: | NUMERO DE COLUMNA: | DESCRIPCION: |
/? | Linea 5 | COLUMNA 1 | Se esperaba: $EOF$ y llegó: struct
start {}
struct Xd {}