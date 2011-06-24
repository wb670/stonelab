/*
 * lab.c
 *
 *  Created on: 2011-6-10
 *      Author: stone
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

int main() {
	char *mem;
	while (1) {
		mem = malloc(1024);
	}
	return 0;
}

void copy(char *src, char *target) {
	FILE *in, *out;
	in = fopen(src, "rb");
	out = fopen(target, "wb");
	int c;
	while ((c = getc(in)) != EOF) {
		putc(c,out);
	}
	fclose(in);
	fclose(out);
}

void i2b(int i) {
	int src = i;
	char ret[32];
	int j;
	ret[31] = (char) (i & 1) + '0';
	for (j = 0; j < 31; ++j) {
		char b = (char) (i >>= 1) & 1;
		ret[32 - j - 2] = b + '0';
	}
	printf("%d==>%s\n", src, ret);
}

