/*
 * signal.c
 *
 *  Created on: 2013-8-19
 *      Author: stone
 */

#include <signal.h>
#include <unistd.h>
#include <stdio.h>

void callback(int sig)
{
	printf("sig is %d. \n", sig);
	printf("pid is %d. \n", getpid());
}

void signal_main()
{
	printf("main: pid is %d. \n", getpid());
	signal(30, callback);
	signal(31, callback);

	sleep(10000000);
}

