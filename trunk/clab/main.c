/*
 * main.c
 *
 * Created on: Jan 19, 2012
 * Author: stone
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define FIRST_TSS_ENTRY 4
#define _TSS(n) ((((unsigned long) n)<<4)+(FIRST_TSS_ENTRY<<3))

void print_int(int i)
{
	printf("%d\n", i);
}

int main(int argc, char **argv)
{
	print_int(4 << 3);
	print_int(5 << 3);

	print_int(_TSS(1));
	print_int(_TSS(2));
	print_int(_TSS(3));
	return 1;
}

