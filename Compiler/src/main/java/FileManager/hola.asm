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
	 divisionErrorMessage: .asciiz "ERROR: DIVISION POR CERO"

.text
.globl main
main:
	li $v0, 1
	li $t9, 1
	push
	jal default_unequal
	# Termino ejecución
	li $v0, 10
	syscall

default_sum:	# sumamos lo que está en el acumulador y lo que podemos popear del stack
	pop
	add $v0, $v0, $t9
	jr $ra

default_sub:	# restamos lo que está en el acumulador y lo que podemos popear del stack
	pop
	sub $v0, $v0, $t9
	jr $ra

default_mul:	# multiplicamos lo que está en el acumulador y lo que podemos popear del stack
	pop
	mul $v0,$v0,$t9
	jr $ra

division_zero:	 #Manejo de error de división por cero
	la $a0, divisionErrorMessage
	li $v0,4
	syscall 
	li $v0,10
	syscall

default_div:	# dividimos lo que está en el acumulador y lo que podemos popear del stack
	pop
	beq $t9,$zero,division_zero #Si el divisor es cero, salto a error
	div $v0, $t9 #El resultado se guarda en registro lo
	mflo $v0 #Se accede a lo con mflo
	jr $ra

default_module:	# modulo lo que está en el acumulador y lo que podemos popear del stack
	pop
	beq $t9,$zero,division_zero #Si el divisor es cero, salto a error
	div $v0, $t9 #El resultado se guarda en registro hi
	mfhi $v0 #Se accede a lo con mfhi
	jr $ra

default_and:	# and entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	and $v0,$v0,$t9
	jr $ra

default_or:	# or entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	or $v0,$v0,$t9
	jr $ra

default_minor:	# menor entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	slt $v0,$v0,$t9 # $v0 = 1 si izquierdo < derecho, de lo contrario $v0 = 0
	jr $ra 

default_major:	# mayor entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	slt $v0,$t9,$v0 # $v0 = 1 si derecho < izquierdo, de lo contrario $v0 = 0 (intercambiamos lugares)
	jr $ra 

default_minor_equal:	# menor o igual entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	slt $v0,$v0,$t9 # $v0 = 1 si izquierdo < derecho, de lo contrario $v0 = 0
	xori $v0,$v0,1 # Invertir el resultado para obtener menor o igual
	jr $ra 

default_major_equal:	# mayor o igual entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	slt $v0,$t9,$v0 # $v0 = 1 si derecho < izquierdo, de lo contrario $v0 = 0 (intercambiamos lugares)
	xori $v0,$v0,1 # Invertir el resultado para obtener mayor o igual
	jr $ra 

default_equal:	# igualdad entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	xor $v0,$v0,$t9 # Si $t0 == $t1, entonces $a0 será 0 (realiza una operación XOR bit a bit entre los registros)
	sltiu $v0,$v0,1 #Si $v0 < 1, entonces $a0 se establecerá en 1 , de lo contrario en 0 
	jr $ra

default_unequal:	# desigualdad entre lo que está en el acumulador y lo que podemos popear del stack
	pop
	xor $v0,$v0,$t9 # Si $v0 == $t9, entonces $v0 será 0
	sltu $v0,$zero,$a0 # Si $v0 != 0, entonces $v0 se establecerá en 1, de lo contrario en 0
	jr $ra

