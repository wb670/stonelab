/*
 * sock.c
 *
 *  Created on: 2013-8-15
 *      Author: stone
 */

#include <netinet/in.h>
#include <sys/socket.h>
#include <stdio.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <event2/event_struct.h>
#include <event2/event_compat.h>
#include <event2/event.h>
#include <stddef.h>

void sock_main()
{
	if (fork() == 0)
	{
		printf("client running...\n");
		sock_client();
	}
	else
	{
		printf("server running...\n");
		sock_server();
	}
}

void sock_server()
{
	int server_fd, client_fd, i = 0;
	int server_len, client_len;
	char ch;
	struct sockaddr_in server_addr;
	struct sockaddr_in client_addr;

	server_fd = socket(AF_INET, SOCK_STREAM, 0);

	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(9999);
	server_addr.sin_addr.s_addr = inet_addr("0.0.0.0");
	server_len = sizeof(server_addr);
	bind(server_fd, (struct sockaddr *) &server_addr, sizeof(server_addr));

	listen(server_fd, 5);

	while (1)
	{
		printf("server is waiting...\n");
		client_len = sizeof(client_addr);
		client_fd = accept(server_fd, (struct sockaddr *) &client_addr, &client_len);

		read(client_fd, &ch, 1);
		ch++;
		i++;
		write(client_fd, &ch, 1);
		write(client_fd, &i, 1);
		close(client_fd);
	}
}

void sock_client()
{
	int sockfd, len;
	struct sockaddr_in addr;
	char ch = 'A', i = '0';

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	addr.sin_family = AF_INET;
	addr.sin_port = htons(9999);
	addr.sin_addr.s_addr = inet_addr("127.0.0.1");
	len = sizeof(addr);

	int ret = connect(sockfd, (struct sockaddr *) &addr, len);
	if (ret == -1)
	{
		printf("connect fail. \n");
		return;
	}

	write(sockfd, &ch, 1);
	read(sockfd, &ch, 1);
	read(sockfd, &i, 1);
	printf("char from server: [ch=%c, i=%c]", ch, i);
	close(sockfd);
}
