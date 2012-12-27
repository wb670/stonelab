#include <stdarg.h>
#include <stdio.h>

void valist(char *fmt, ...)
{
	va_list args;
	va_start(args, fmt);
	int v = 0;
	while (v != -1)
	{
		v = va_arg(args, int);
		printf("%d\n", v);
	}
	va_end(args);
	return;
}

int main(int argc, char **argv)
{
	int c = -1;
	printf("%d\n", c >> 1);
	if (c)
		printf("if c=%d\n", c);
	else
		printf("else c=%d\n", c);
	return 0;
}

