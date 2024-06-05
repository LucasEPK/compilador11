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
	li $v0, 0
	li $t9, 4
	push
	jal default_module
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
	div $v0, $t9 #El resultado se guarda en registro lo
	mfhi $v0 #Se accede a lo con mflo
	jr $ra

