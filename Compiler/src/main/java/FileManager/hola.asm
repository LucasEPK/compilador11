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
Str_vtable:
	.word Str_length
	.word Str_concat

Array_vtable:
	.word Array_length

IO_vtable:
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

Hola_vtable:
	.word Hola_m1

	 divisionErrorMessage: .asciiz "ERROR: DIVISION POR CERO"

.text
.globl main
main:
	default_while:
	# Condición while

	#Generando código de ExpBin

	#Generando código de left
	move $t9, $v0
	push #Pusheo valor de left en el stack 

	#Generando código de right
	li $v0, 2

	#Generando código de operación
	jal default_equal
	# Fin condición while
	bne $v0, 1, default_while_fin	#si la condición del while no es verdadera salta todo el while
	# Cuerpo while

	#Generando código de ExpBin

	#Generando código de left
	li $v0, 2
	move $t9, $v0
	push #Pusheo valor de left en el stack 

	#Generando código de right
	li $v0, 2

	#Generando código de operación
	jal default_sum
	# Fin cuerpo while
	j default_while	# vuelve al pricipio del while a chequear la condición
	default_while_fin:
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