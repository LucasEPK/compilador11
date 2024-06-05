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

.text
.globl main
main:
	li $v0, 3
	li $t9, 4
	push
	jal default_sum
	# Termino ejecución
	li $v0, 10
	syscall

default_sum:	# sumamos lo que está en el acumulador y lo que podemos popear del stack
	pop
	add $v0, $v0, $t9
	jr $ra