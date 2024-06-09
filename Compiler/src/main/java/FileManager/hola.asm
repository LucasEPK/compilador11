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
	# FIN declaracion de variables
IO_in_char:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

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
	# FIN declaracion de variables
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
	# FIN declaracion de variables
Char_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
Str_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
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
	# FIN declaracion de variables
Object_constructor:
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	# FIN declaracion de variables
	# Return
	li $v0, 3
	la $t9,$v0 #cargo en $t9 el valor de retorno
	push #Lo pusheo al stack
	lw $ra,0($fp) #Recupero el return adress
	jr $ra #Vuelvo al return adress
	 #Fin Return
main:	# METODO START ----------------------------------------------------------
	# Actualizacion de framepointer
	la $t9, ($fp)		# Metemos el framepointer anterior en el stack
	push
	la $t9, ($ra)		# Metemos el return address en el stack
	push
	addi $fp, $sp, 4	# Colocamos el frame pointer apuntando al tope de la pila, adonde está guardado $ra
	# FIN actualizacion de framepointer

	# Declaracion de variables
	li $t9, 0 # Reservamos un espacio en el stack para esta variable;
	push
	# FIN declaracion de variables
	lw $v0, -4($fp)	# Meto el valor asignado de la variable del stack en el acumulador ($v0)
addiu $v0,$v0, 1 #++
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