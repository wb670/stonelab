/*
 * sock.c
 *
 *  Created on: Jan 19, 2012
 *  Author: stone
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <errno.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>

#define PORT 9999
#define BUF_SIZE 1024
#define MAX_CONN 1

int client_fds[MAX_CONN];
int client_count = 0;

void sock_demo()
{
	int sock_fd;
	int new_fd;
	int reuse = 1;
	int i, ret;
	char buf[BUF_SIZE + 1];

	struct sockaddr_in server_addr;
	struct sockaddr_in client_addr;

	if ((sock_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1)
	{
		printf("create socket fail.");
		exit(-1);
	}

	if (setsockopt(sock_fd, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(int)) == -1)
	{
		printf("set socket opt fail.");
		exit(-1);
	}

	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(PORT);
	server_addr.sin_addr.s_addr = INADDR_ANY;
	memset(server_addr.sin_zero, '\0', sizeof(server_addr.sin_zero));

	if (bind(sock_fd, (struct sockaddr *) &server_addr, sizeof(server_addr)) == -1)
	{
		printf("bind fail.");
		exit(-1);
	}

	if (listen(sock_fd, 5) == -1)
	{

		printf("listen fail.");
		exit(-1);
	}

	fd_set fdsr;
	int maxsock = sock_fd;
	struct timeval tv;
	socklen_t len = sizeof(client_addr);
	while (true)
	{
		FD_ZERO(&fdsr);
		FD_SET(sock_fd, &fdsr);

		for (i = 0; i < MAX_CONN; i++)
		{
			if (client_fds[i] != 0)
			{
				FD_SET(client_fds[i], &fdsr);
			}
		}

		tv.tv_sec = 10;
		ret = select(maxsock + 1, &fdsr, NULL, NULL, &tv);

		if (ret <= 0)
			continue;

		// process accept event
		if (FD_ISSET(sock_fd,&fdsr))
		{
			if ((new_fd = accept(sock_fd, (struct sockaddr *) &client_addr, &len)) == -1)
			{
				printf("accept fail.\n");
				continue;
			}

			if (client_count == MAX_CONN)
			{
				send(new_fd, "MAX\n", 5, 0);
				close(new_fd);
				continue;
			}

			client_fds[client_count++] = new_fd;
			printf("new connection accepted. client [%s:%d]\n", inet_ntoa(client_addr.sin_addr),
					ntohs(client_addr.sin_port));

			if (new_fd > maxsock)
				maxsock = new_fd;
		}

		// process read event
		int i;
		for (i = 0; i < client_count; ++i)
		{
			if (FD_ISSET(client_fds[i],&fdsr))
			{
				ret = recv(client_fds[i], buf, BUF_SIZE, 0);
				if (ret > 0)
				{
					memset(&buf[ret], '\0', 1);
					printf("recv new msg:%s", buf);
					send(client_fds[i], "ACK\n", 5, 0);

					for (i = 0; i < 10; ++i)
					{
						printf("%d:%c\n", buf[i], buf[i]);
					}

				}
				else
				{
					printf(">>>>>>>>>closing....");
					close(client_fds[i]);
					FD_CLR(client_fds[i], &fdsr);
					client_fds[i] = 0;
				}
			}

		}

	}
}
