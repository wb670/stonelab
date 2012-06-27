/*
 * c.c
 *
 *  Created on: May 14, 2012
 *      Author: stone
 */

#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>
#include <stdio.h>
#include <time.h>

#define m_lock() pthread_mutex_lock(&mutex);
#define s_lock() pthread_spin_lock(&spin);
#define m_unlock() pthread_mutex_unlock(&mutex);
#define s_unlock() pthread_spin_unlock(&spin);

int dur = 1;
int n_loop = 1000000000;
int n_threads = 50;

pthread_mutex_t mutex;
pthread_spinlock_t spin;
int m_count;
int s_count;

void * m_handler()
{
	int i;
	for (i = 0; i < n_loop; ++i)
	{
		m_lock();
		m_count++;
		m_unlock();
	}
	return NULL;
}

void *s_handler()
{
	int i;
	for (i = 0; i < n_loop; ++i)
	{
		s_lock();
		s_count++;
		s_unlock();
	}

	return NULL;
}

void lock_main()
{
	pthread_spin_init(&spin, 0);
	pthread_mutex_init(&mutex, NULL);

	pthread_t m_threads[n_threads];
	pthread_t s_threads[n_threads];

	int i;
	for (i = 0; i < n_threads; ++i)
	{
		pthread_create(&s_threads[i], NULL, &s_handler, NULL);
		pthread_create(&m_threads[i], NULL, &m_handler, NULL);
	}

	sleep(20);
	printf("m counts: %d. \n", m_count);
	printf("s counts: %d. \n", s_count);

}
