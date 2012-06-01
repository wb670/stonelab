/*
 * a.c
 *
 *  Created on: May 14, 2012
 *      Author: stone
 */

#include <pthread.h>
#include <semaphore.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

#define PN 4
#define CN 2
#define MAX 10

int products[MAX];

sem_t sem_p, sem_c;
pthread_mutex_t mutex;
int id;
int count;

int rand_int(int range)
{
	srand(0);
	return rand() % range + 1;
}

void produce()
{
	while (1)
	{
		sleep(rand_int(5));

		sem_wait(&sem_p);

		pthread_mutex_lock(&mutex);
		products[count] = ++id;
		count++;
		pthread_mutex_unlock(&mutex);

		sem_post(&sem_c);
	}
}

void consume()
{
	while (1)
	{
		sleep(rand_int(5));

		sem_wait(&sem_c);

		pthread_mutex_lock(&mutex);
		products[count] = 0;
		count--;
		pthread_mutex_unlock(&mutex);

		sem_post(&sem_p);
	}
}

void trace()
{
	while (1)
	{
		sleep(2);

		int i;
		pthread_mutex_lock(&mutex);
		for (i = 0; i < MAX; ++i)
		{
			printf("%d ", products[i]);
		}
		printf("\n");
		pthread_mutex_unlock(&mutex);

	}
}

void pc_main()
{
	pthread_mutex_init(&mutex, NULL);
	sem_init(&sem_p, 0, 10);
	sem_init(&sem_c, 0, 0);

	int i;
	for (i = 0; i < PN; ++i)
	{
		pthread_t t;
		pthread_create(&t, NULL, &produce, NULL);
	}

	for (i = 0; i < CN; ++i)
	{
		pthread_t t;
		pthread_create(&t, NULL, &consume, NULL);
	}

	pthread_t t;
	pthread_create(&t, NULL, &trace, NULL);
}
