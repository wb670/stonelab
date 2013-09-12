#include <stdio.h>
#include <unistd.h>
#include <sys/resource.h>
#include <pwd.h>

void func()
{
	static int a;
	a++;
	printf("%d\n", a);
}

int main(int argc, char **argv)
{
	int i;
	for (i = 0; i < 10; ++i)
	{
		func();
	}
	static int a;
	printf(">>>>>>%d\n", a);
	return 1;
}

