# Macros
.macro push			# hace push en el stack y guarda t9 en el stack
	sw $t9, 0($sp)
	addiu $sp, $sp, -4
.end_macro 
.macro pop			# hace pop en el stack y guarda el elemento popeado en t9
	lw $t9, 4($sp)
	addiu $sp, $sp, 4
.end_macro

.data
# Vtables
Object_vtable:
	.word Object_constructor

Int_vtable:
	.word Int_constructor

Str_vtable:
	.word Str_constructor
	.word Str_length
	.word Str_concat

Bool_vtable:
	.word Bool_constructor

Char_vtable:
	.word Char_constructor

Array_vtable:
	.word Array_constructor
	.word Array_length

IO_vtable:
	.word IO_constructor
	.word IO_out_str
	.word IO_out_int
	.word IO_out_bool
	.word IO_out_char
	.word IO_out_array_int
	.word IO_out_array_bool
	.word IO_out_array_str
	.word IO_out_array_char
	.word IO_in_str
	.word IO_in_int
	.word IO_in_bool
	.word IO_in_char

	 divisionErrorMessage: .asciiz "ERROR: DIVISION POR CERO"

.text
.globl main
j main
IO_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# Declaracion de atributos
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, IO_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	# Return de CIR
	la $t9,($v0) #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	 #Fin Return de CIR
IO_in_char:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Funcion in_char
	la $v0, 12 #Cargo en $v0 el syscall de char 
	syscall
	# Fin funcion in_char
	la $t9,($v0) #cargo en $t9 el valor de retorno (para char esta en $v0)
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
IO_in_bool:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Funcion in_bool
	li $v0, 5 #Cargo en $v0 el syscall de int 
	syscall
	# Fin funcion in_bool
	la $t9,($v0) #cargo en $t9 el valor de retorno (para bool esta en $v0)
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
IO_in_int:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Funcion in_int
	li $v0, 5 #Cargo en $v0 el syscall de int 
	syscall
	# Fin funcion in_int
	la $t9,($v0) #cargo en $t9 el valor de retorno (para int esta en $v0)
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
IO_in_str:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	.data
	input_string: .space  1025 #reservo espacio
	.text
	# Funcion in_str
	li $v0, 8 #Cargo en $v0 el syscall de str 
	la $a0, input_string #Cargo en $a0 la dirección de la variable donde se guardará el string
	li $a1, 1025 #Cargo en $a1 la cantidad de bytes que se pueden leer
	syscall
	# Fin funcion in_str
	la $t9,input_string #cargo en $t9 el valor de retorno (para str esta input_string
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
IO_out_array_char:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
IO_out_array_bool:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
IO_out_array_str:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
IO_out_array_int:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
IO_out_char:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Funcion out_char
	lw $a0, 12($fp) #Cargo el valor del argumento a imprimir (esta en 12($fp))
	li $v0, 11 #Cargo en $v0 el syscall de char 
	syscall
	# Fin funcion out_char
	li $t9,0 #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
IO_out_bool:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Funcion out_bool
	lw $a0, 12($fp) #Cargo el valor del argumento a imprimir (esta en 12($fp))
	li $v0, 1 #Cargo en $v0 el syscall de bool (representado por int) 
	syscall
	# Fin funcion out_bool
	li $t9,0 #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
IO_out_int:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Funcion out_int
	lw $a0, 12($fp) #Cargo el valor del argumento a imprimir (esta en 12($fp))
	li $v0, 1 #Cargo en $v0 el syscall de int 
	syscall
	# Fin funcion out_int
	li $t9,0 #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
IO_out_str:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Funcion out_str
	lw $a0, 12($fp) #Cargo el valor del argumento a imprimir (esta en 12($fp))
	li $v0, 4 #Cargo en $v0 el syscall de string 
	syscall
	# Fin funcion out_Str
	li $t9,0 #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	# Declaracion de variables
	# FIN declaracion de variables
Array_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# Declaracion de atributos
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, Array_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	# Return de CIR
	la $t9,($v0) #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	 #Fin Return de CIR
Array_length:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
Bool_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# Declaracion de atributos
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, Bool_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	# Return de CIR
	la $t9,($v0) #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	 #Fin Return de CIR
Char_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# Declaracion de atributos
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, Char_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	# Return de CIR
	la $t9,($v0) #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	 #Fin Return de CIR
Str_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# Declaracion de atributos
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, Str_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	# Return de CIR
	la $t9,($v0) #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	 #Fin Return de CIR
Str_length:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
Str_concat:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
Int_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# Declaracion de atributos
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, Int_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	# Return de CIR
	la $t9,($v0) #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	 #Fin Return de CIR
Object_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# Declaracion de atributos
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, Object_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	# Return de CIR
	la $t9,($v0) #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return address
	jr $ra #Vuelvo al return address
	 #Fin Return de CIR
main:	# METODO START ----------------------------------------------------------
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
	li $v0, 9	# Aloco memoria en el heap
	li $a0, 4	# x bytes en memoria
	syscall		# Con esto tenemos la referencia en $v0
	la $t1, IO_vtable	# Guardamos la dirección de la vtable en la primera posicion del heap
	sw $t1, 0($v0)
	#Guardamos el CIR en $s4
	la $s4, ($v0)	#Guardamos el CIR en $s4

	#Generando código de ExpBin

	#Generando código de left
	li $v0, 3
	move $t9, $v0
	push #Pusheo valor de left en el stack 

	#Generando código de right
	li $v0, 0

	#Generando código de operación
	jal default_div
	la $t9, ($v0)	# Cargamos el argumento en $t9
	push	# Push de parametros 0
	la $t9, ($s4)	# Caso base, se agrega el cir que tenemos en $s4 por el encadenado
	push	# Push de puntero al objeto
	jal IO_out_int	# Salto a la función desde un encadenado
	# Desapilamos el RA completo de la función llamada
	pop	# Pop del valor de retorno
	la $v0, ($t9)
	pop	# Pop de puntero de retorno $ra de la función llamada
	pop	# Pop del framepointer anterior que perdimos
	add $fp, $zero, $t9	# Volvemos a cargar el framepointer correcto
	pop	# Pop de puntero al objeto
	pop	# Pop de parametro 0
	# FIN desapilado del RA completo de la función llamada
	# Termino ejecución
	li $v0, 10
	syscall

default_sum:	# sumamos lo que está en el tope del stack y el acumulador 
	pop
	add $v0, $t9, $v0
	jr $ra

default_sub:	# restamos lo que está en el tope del stack y el acumulador
	pop
	sub $v0, $t9, $v0
	jr $ra

default_mul:	# multiplicamos lo que está en el tope del stack y el acumulador
	pop
	mul $v0,$t9,$v0
	jr $ra

division_zero:	 #Manejo de error de división por cero
	la $a0, divisionErrorMessage
	li $v0,4
	syscall 
	li $v0,10
	syscall

default_div:	# dividimos lo que está en el tope del stack y el acumulador
	pop
	beq $v0,$zero,division_zero #Si el divisor es cero, salto a error
	div $t9, $v0 #El resultado se guarda en registro lo
	mflo $v0 #Se accede a lo con mflo
	jr $ra

default_module:	# modulo lo que está en el tope del stack y el acumulador
	pop
	beq $v0,$zero,division_zero #Si el divisor es cero, salto a error
	div $t9, $v0 #El resultado se guarda en registro hi
	mfhi $v0 #Se accede a lo con mfhi
	jr $ra

default_and:	# and entre lo que está en el tope del stack y el acumulador
	pop
	and $v0,$t9,$v0
	jr $ra

default_or:	# or entre lo que está en el tope del stack y el acumulador
	pop
	or $v0,$t9,$v0
	jr $ra

default_minor:	# menor entre lo que está en el tope del stack y el acumulador
	pop
	slt $v0,$t9,$v0 # $v0 = 1 si izquierdo < derecho, de lo contrario $v0 = 0
	jr $ra 

default_major:	# mayor entre lo que está en el tope del stack y el acumulador
	pop
	slt $v0,$v0,$t9 # $v0 = 1 si derecho < izquierdo, de lo contrario $v0 = 0 (intercambiamos lugares)
	jr $ra 

default_minor_equal:	# menor o igual entre lo que está en el tope del stack y el acumulador
	pop
	slt $v0,$v0,$t9 # $v0 = 1 si derecho < izquierdo, de lo contrario $v0 = 0 (intercambiamos lugares)
	xori $v0,$v0,1 # Invertir el resultado para obtener menor o igual
	jr $ra 

default_major_equal:	# mayor o igual entre lo que está en el tope del stack y el acumulador
	pop
	slt $v0,$t9,$v0 # $v0 = 1 si izquierdo < derecho, de lo contrario $v0 = 0
	xori $v0,$v0,1 # Invertir el resultado para obtener mayor o igual
	jr $ra 

default_equal:	# igualdad entre lo que está en el tope del stack y el acumulador
	pop
	xor $v0,$t9,$v0 # Si $t0 == $t1, entonces $a0 será 0 (realiza una operación XOR bit a bit entre los registros)
	sltiu $v0,$v0,1 #Si $v0 < 1, entonces $a0 se establecerá en 1 , de lo contrario en 0 
	jr $ra

default_unequal:	# desigualdad entre lo que está en el tope del stack y el acumulador
	pop
	xor $v0,$t9,$v0 # Si $t9 == $v0, entonces $v0 será 0 sino otro numero random
	slt $v0, $zero, $v0 	# Si v0 > 0 entonces v0 = 1 sino v0 = 0
	jr $ra