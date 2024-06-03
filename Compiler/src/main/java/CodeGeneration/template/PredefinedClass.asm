#Clases Predefinidas
.data
test_str: .asciiz "Hola, mundo!"
empty_str: .word 0
Object_vtable:
IO_vtable:
	.word out_str
	.word out_int
	.word out_bool
	.word out_char
	.word out_array_int
	.word out_array_str
	.word out_array_bool
	.word out_array_char
	.word in_str
	.word in_int
	.word in_bool
	.word in_char
Array_vtable:
	.word length_array
Int_vtable:
Str_vtable:
	.word length_str
	.word concat_str
Bool_vtable:
Char_vtable:

.text
.globl main

main:
li $v0, 10
syscall
    

constructor_Object:
la $t0, Object_vtable # Cargar la dirección de Object_vtable en $t0
sw $t0, ($v0) # Almacenar la dirección de la vtable en la primera palabra de la instancia
jr $ra # Retornar al llamador

constructor_Int:
li $a0,8
li $v0,9
syscall
#Se tiene que inicializar en 0
la $t0,Int_vtable # Cargar la dirección de Int_vtable en $t0
sw $t0,0($v0) # Almacenar la dirección de la vtable en la primera palabra de la instancia
li $t0,0 # Cargar 0 en $t0
sw $t0, 4($v0) # Almacenar 0 en la segunda palabra de la instancia (el valor del Int)
jr $ra	# Retornar al llamador

constructor_Char:
#Se inicializa en ' '
la $t0,Char_vtable # Cargar la dirección de Char_vtable en $t0
sw $t0,0($a0) # Almacenar la dirección de la vtable en la primera palabra de la instancia
lw $t0,' ' # Cargar ' ' en $t0
sw $t0,4($a0) # Almacenar ' ' en la segunda palabra de la instancia (el valor del Char)
jr $ra # Retornar al llamador

consructor_Bool:
#Se inicializa en 0 (false)
la $t0,Bool_vtable # Cargar la dirección de Bool_vtable en $t0
sw $t0, 0($a0) # Almacenar la dirección de la vtable en la primera palabra de la instancia
lw $t0,0 # Cargar 0 en $t0
sw $t0,4($a0) # Almacenar 0 en la segunda palabra de la instancia (el valor del Bool)
jr $ra # Retornar al llamador

constructor_Str:
li $a0,12
li $v0,9
syscall
#Se inicializa en ""
la $t0,Str_vtable # Cargar la dirección de Bool_vtable en $t0
sw $t0,0($v0) # Almacenar la dirección de la vtable en la primera palabra de la instancia
lw $t0,empty_str # Cargar "" en $t0
sw $zero,4($v0) #Almacenar el tamaño del String en la segunda palabra (0)
sw $t0,8($v0) # Almacenar "" en la tercera palabra de la instancia (el valor del Str)
jr $ra # Retornar al llamador

length_str:
#Devuelve length del parámetro self (la longitud esta en la segunda palabra)
la $t0,4($fp)
lw $v0, 4($t0) #Lo almaceno en v0 ya que usamos v0,v1 para reusltados
jr $ra

concat_str:


constructor_Array:
la $t0,Array_vtable # Cargar la dirección de Array_vtable en $t0
sw $t0, 0($a0) # Almacenar la dirección de la vtable en la primera palabra de la instancia
#Se tiene que definir por defecto segun el tipo primitivo que tenga dentro (Int,Str,Char,Bool)
#Actualmente solo lo vamos a definir como cero independietemente del tipo debido los tiempos
lw $t0,0
sw $t0,4($a0) #Almaceno la longiutd del Array en segunda palabra
lw $t0,0 
sw $t0,8($a0) #Almaceno incialización del array en tercera palabra
jr $ra

length_array:
# length devuelve la longitud del par´ametro self (segunda palabra)
la $t0, 4($fp)
lw $v0, 4($v0) #Lo almaceno en v0 ya que usamos v0,v1 para reusltados
jr $ra


constructor_IO: #new IO
la $t0,IO_vtable #Puntero a objeto de su VT
sw $t0,0($a0) # Almacenar la dirección de la vtable en la primera palabra de la instancia
jr $ra # Retornar al llamador


out_str:
#Se pasa como parámetro el CIR del string a imprimir
#El string en nuestro Cir se encuentra en la tercera palabra
#primer palabra: referencia a VT, segunda longitud, tercera String
la $t0, 8($fp)
la $a0,($t0) #Guardo el valor del String en $a0
li $v0,4 	
syscall #Hago un Syscall para imprimir el valor
jr $ra


out_int:
#Se pasa como parámetro el CIR del int a imprimir
la $t0, 4($fp)
la $a0,($t0)
li $v0,1
syscall
jr $ra

out_bool:
#Se pasa como parámetro el CIR del bool a imprimir
la $t0, 4($fp)	
la $a0, ($t0)	
li $v0, 1
syscall
jr $ra

out_char:
#Se pasa como parámetro el CIR del char a imprimir
la $t0, 4($fp)	
la $a0, ($t0)	
li $v0, 4
syscall
jr $ra


out_array_int:
out_array_str:
out_array_bool:
out_array_char:

in_str:
jal constructor_Str
la $t0,($v0) #Guardo CIR del Str
la $a0, 8($t0) #Dirección donde se guarda el Str (tercer palabra)
li $v0,8
syscall
jr $ra


in_int:
jal constructor_Int
la $t0,($v0) #Guardo CIR del Int
la $a0, 4($t0) #Dirección donde se guarda el int
li $v0,5
syscall
jr $ra

in_bool:
jal constructor_Bool
la $t0,($v0) #Guardo CIR del Bool
la $a0, 4($t0) #Dirección donde se guarda el bool
li $v0,5
syscall
jr $ra

in_char: 
jal constructor_Char
la $t0,($v0) #Guardo CIR del Char
la $a0, 4($t0) #Dirección donde se guarda el char
li $v0,8
syscall
jr $ra

	
