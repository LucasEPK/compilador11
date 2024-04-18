# Consideraciones

- [] Agregar atributo Token a las clases para poder acceder al lexéma, columna, fila y token
- [] En el código saber en que clase y método actual estoy
- [] Booleano para saber si la clase fué chequeada o no
- [] Consolidación debe evaluar todo el tema de herencia 
  - [] Atributos y métodos heredados 
    - [] Tener en cuenta actualizacion de pos de métodos y atributos heredados (por más que sean privados)
  - [] Herencia ciclica
  - [] Métodos sobrescriptos
- [] Harcodear las clases:
  - [] IO
  - [] Array
  - [] Int
  - [] Str
  - [] Bool
  - [] Char

# Restricciones Sémanticas

- [] No puede haber dos struct con mismo nombre
- [] Una clase no puede tener más de un impl
- [] Subclase no puede acceder ni redefinir atributos privados de la superclase (Solo puede acceder a través de sus métodos)
- [] Todos los métodos tienen alcance global. Aquellos métodos que tengan antes de la palabra fn la palabra  clave st ser´an m´etodos de clase. No puede accederse a una variable de instancia en un contexto  estático.
- [] 2 atributos o 2 métodos no pueden tener el mismo nombre pero un método y un atributo si
- [] Verificar que la firma de un método heredado sobrescripto sea igual
  - [] Si no se especifica herencia, hererda por defecto de "Object"
  - [] Solo se puede heredar de una unica clase
  - [] No pueden haber ciclos en la herencia
  - [] Si B hereda de A, A debe tener un Struct definido
  - [] si una clase B hereda un m´etodo f de una superclase A, entonces B puede anular la definici´on heredada de f siempre que el n´umero de argumentos, los tipos de parámetros formales y el tipo de retorno sean exactamente iguales
- [] No se pueden redefinir atributos
- [] Dos argumentos pasados como parámetro no pueden tener el mismo nombre
- [] No pueden haber ids dentro del método con el mismo nombre que los parámetros
- [] Método constructor es obligatorio para una Clase