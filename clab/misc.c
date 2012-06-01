/*
 * misc.c
 *
 *  Created on: May 23, 2012
 *  Author: stone
 */

#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

void misc()
{
	pid_t p = fork();
	if (p == 0)
	{
		printf("child process:%d\n", getpid());
		return;
	}
	else
	{
		printf("main process:%d\n", getpid());
	}

	printf("all:%d\n", getpid());
}
