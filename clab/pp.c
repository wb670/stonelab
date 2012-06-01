/*
 * c.c
 *
 *  Created on: May 14, 2012
 *      Author: stone
 */

#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>

sem_t sem;
pthread_spinlock_t spin;

void pp_produce()
{
	pthread_spin_lock(&spin);

	sleep(100000000);

	pthread_spin_unlock(&spin);
}

void pp_consume()
{
	pthread_spin_lock(&spin);

	sleep(100000000);

	pthread_spin_unlock(&spin);
}

void pp_main()
{
	pthread_t t1, t2;
	pthread_spin_init(&spin, 0);

	pthread_create(&t1, NULL, &pp_produce, NULL);
	pthread_create(&t2, NULL, &pp_consume, NULL);

	pthread_join(&t1, NULL);
	pthread_join(&t2, NULL);
}

