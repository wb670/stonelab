#include <libnet.h>
#include <stdio.h>
#include <netinet/if_ether.h>
#include <net/if_arp.h>
#include <pcap.h>

void arp_send()
{
	char* device = "en0";
	char* src_ip_str = "10.68.46.36";
	char* dst_ip_str = "10.68.46.21";
	u_int8_t src_mac[6] =
	{ 0x20, 0xc9, 0xd0, 0x44, 0x28, 0xe9 };
	u_int8_t dst_mac[6] =
	{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	u_int8_t broadcast_mac[6] =
	{ 0xff, 0xff, 0xff, 0xff, 0xff, 0xff };
	int packet_size;
	u_int32_t src_ip, dst_ip;
	char error[LIBNET_ERRBUF_SIZE];

	libnet_t* handle;
	libnet_ptag_t arp_proto_tag, eth_proto_tag;

	src_ip = libnet_name2addr4(NULL, src_ip_str, LIBNET_RESOLVE);
	dst_ip = libnet_name2addr4(NULL, dst_ip_str, LIBNET_RESOLVE);

	if (src_ip == (uint8_t) -1 || dst_ip == (uint8_t) -1)
	{
		printf("ip address convert error. \n");
		exit(-1);
	}

	handle = libnet_init(LIBNET_LINK_ADV, device, error);
	if (handle == NULL)
	{
		printf("libnet_init error [%s]. \n", error);
		exit(-1);
	}

	arp_proto_tag = libnet_build_arp( //libnet_build_arp
			ARPHRD_ETHER,//hardware type
			ETHERTYPE_IP, // protocol type
			6, //hardware size
			4, //protocol size
			ARPOP_REQUEST, // op code
			src_mac, // source mac addr
			(u_int8_t*) &src_ip, // source ip
			dst_mac, // target mac addr
			(u_int8_t*) &dst_ip, // target ip
			NULL, // no payload
			0, //payload length
			handle, // libnet
			0 //create new one
			);
	if (arp_proto_tag == -1)
	{
		printf("build arp protocol error. \n");
		exit(-1);
	}

	eth_proto_tag = libnet_build_ethernet( //libnet_build_ethernet
			broadcast_mac, //broadcast mac addr
			src_mac, // source mac addr
			ETHERTYPE_ARP, // protocol type
			NULL, //payload
			0, // payload length
			handle, // libnet
			0 // create new one
			);
	if (eth_proto_tag == -1)
	{
		printf("build erhernet error. \n");
		exit(-1);
	}

	packet_size = libnet_write(handle);
	libnet_destroy(handle);

	printf("arp_send successfully. [dst_ip:%s; packet_size:%d] \n", dst_ip_str,
			packet_size);
}

void arp_reply()
{
	char* device = "en0";
	char* src_ip_str = "10.68.46.36";
	char* dst_ip_str = "10.68.46.36";
	u_int8_t src_mac[6] =
	{ 0x20, 0xc9, 0xd0, 0x44, 0x28, 0xe9 };
	u_int8_t dst_mac[6] =
	{ 0x20, 0xc9, 0xd0, 0x44, 0x28, 0xe9 };
	int packet_size;
	u_int32_t src_ip, dst_ip;
	char error[LIBNET_ERRBUF_SIZE];

	libnet_t* handle;
	libnet_ptag_t arp_proto_tag, eth_proto_tag;

	src_ip = libnet_name2addr4(NULL, src_ip_str, LIBNET_RESOLVE);
	dst_ip = libnet_name2addr4(NULL, dst_ip_str, LIBNET_RESOLVE);

	if (src_ip == (uint8_t) -1 || dst_ip == (uint8_t) -1)
	{
		printf("ip address convert error. \n");
		exit(-1);
	}

	handle = libnet_init(LIBNET_LINK_ADV, device, error);
	if (handle == NULL)
	{
		printf("libnet_init error [%s]. \n", error);
		exit(-1);
	}

	arp_proto_tag = libnet_build_arp( //libnet_build_arp
			ARPHRD_ETHER,// hardware type
			ETHERTYPE_IP, // protocol type
			6, // hardware size
			4, // ip address size
			ARPOP_REPLY, // op code
			src_mac, //source mac
			(uint8_t*) &src_ip, // src_ip ip address
			dst_mac, //target mac
			(uint8_t*) &dst_ip, //target ip address
			NULL, //no payload
			0, // payload length
			handle, // libnet
			0 //create new one
			);

	if (arp_proto_tag == -1)
	{
		printf("build arp protocol error. \n");
		exit(-1);
	}

	eth_proto_tag = libnet_build_ethernet( //libnet_build_ethernet
			dst_mac, // target mac
			src_mac, // source mac
			ETHERTYPE_ARP, // protocol type
			NULL, // no payload
			0, // payload length
			handle, // libnet
			0 //create new one
			);

	if (eth_proto_tag == -1)
	{
		printf("build ethernet protocol error. \n");
		exit(-1);
	}

	packet_size = libnet_write(handle);
	libnet_destroy(handle);

	printf("arp_reply successfully. [dst_ip:%s; packet_size:%d] \n", dst_ip_str,
			packet_size);
}

/* send arp package with socket */
// it's fail on MacOS, success on Linux.
void arp_rawsend()
{
	char* device = "en0";
	char* src_ip_str = "10.68.46.36";
	char* dst_ip_str = "10.68.46.21";
	u_int8_t src_mac[6] =
	{ 0x20, 0xc9, 0xd0, 0x44, 0x28, 0xe9 };
	u_int8_t dst_mac[6] =
	{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	u_int8_t broadcast_mac[6] =
	{ 0xff, 0xff, 0xff, 0xff, 0xff, 0xff };

	int i, r, fd;
	struct ether_header header;
	struct ether_arp arp;
	struct sockaddr sa;
	unsigned char packet[sizeof(header) + sizeof(arp)];

	//socket
	// linux fd = socket(AF_INET, SOCK_PACKET, htons(ETH_P_ARP));
	fd = socket(AF_INET, SOCK_RAW, htons(ETHERTYPE_ARP));
	if (fd == -1)
	{
		printf("create socket error. \n");
		exit(-1);
	}

	// device
	memset(&sa, 0, sizeof(struct sockaddr));
	strcpy(sa.sa_data, device);

	// ethernet header
	memset(&header, 0, sizeof(struct ether_header));
	memcpy(header.ether_dhost, broadcast_mac, 6);
	memcpy(header.ether_shost, src_mac, 6);
	header.ether_type = htons(ETHERTYPE_ARP);

	// arp
	memset(&arp, 0, sizeof(struct ether_arp));
	arp.ea_hdr.ar_hrd = htons(ARPHRD_ETHER);
	arp.ea_hdr.ar_pro = htons(ETHERTYPE_IP);
	arp.ea_hdr.ar_hln = ETHER_ADDR_LEN;
	arp.ea_hdr.ar_pln = 4;
	arp.ea_hdr.ar_op = htons(ARPOP_REQUEST);
	memcpy(arp.arp_sha, header.ether_shost, 6);
	int arp_spa = inet_addr(src_ip_str);
	memcpy(arp.arp_spa, &arp_spa, 4);
	memcpy(arp.arp_tha, &dst_mac, 6);
	int arp_tpa = inet_addr(dst_ip_str);
	memcpy(arp.arp_tpa, &arp_tpa, 4);

	// build package
	memcpy(&packet, &header, sizeof(struct ether_header));
	memcpy(&packet[sizeof(struct ether_header)], &arp,
			sizeof(struct ether_arp));

	// debug
	for (i = 0; i < sizeof(packet); ++i)
	{
		if (i % 16 == 0)
		{
			printf("\n");
		}
		printf("%02x ", *(packet + i));
	}

	// send
	r = sendto(fd, &packet, sizeof(packet), 0, &sa, sizeof(sa));
	printf("\n\npackage length: [%d] \n", r);

	// close socket
	r = close(fd);
	if (r == -1)
	{
		printf("close socket error. \n");
		exit(-1);
	}
}

/* ARP Header, (assuming Ethernet+IPv4) */
typedef struct
{
	u_int16_t htype; /* Hardware Type */
	u_int16_t ptype; /* Protocol Type */
	u_int8_t hlen; /* Hardware Address Length */
	u_int8_t plen; /* Protocol Address Length */
	u_int16_t oper; /* Operation Code  */
	u_int8_t sha[6]; /* Sender hardware address */
	u_int8_t spa[4]; /* Sender IP address */
	u_int8_t tha[6]; /* Target hardware address */
	u_int8_t tpa[4]; /* Target IP address */
} arphdr_t;

void arp_on_reply()
{
	char *device = "en0";
	char error[PCAP_ERRBUF_SIZE];

	int i, r;
	pcap_t *dev;
	bpf_u_int32 addr, mask;
	struct bpf_program filter;
	const u_char *packet;
	struct pcap_pkthdr pkthdr;
	arphdr_t *arpheader;

	dev = pcap_open_live(device, 2048, 0, 512, error);
	if (dev == NULL)
	{
		printf("open device error. [%s] \n", error);
		exit(-1);
	}

	r = pcap_lookupnet(device, &addr, &mask, error);
	if (r == -1)
	{
		printf("lookupnet error. [%s] \n", error);
		exit(-1);
	}
	printf("addr=[%08x]; mask=[%08x]. \n", addr, mask);

	r = pcap_compile(dev, &filter, "arp", 1, mask);
	if (r == -1)
	{
		printf("compile error. \n");
		exit(-1);
	}

	r = pcap_setfilter(dev, &filter);
	if (r == -1)
	{
		printf("set filter error. \n");
		exit(-1);
	}

	while (1)
	{
		packet = pcap_next(dev, &pkthdr);
		if (packet == NULL)
		{
			continue;
		}

		arpheader = (arphdr_t *) (packet + 14);
		printf("Hardware type: 0x%02x \n", ntohs(arpheader->htype));
		printf("Protocol type: 0x%04x \n", ntohs(arpheader->ptype));
		printf("Op Code: 0x%02x \n", ntohs(arpheader->oper));

		if (ntohs(arpheader->htype) == 0x01
				&& ntohs(arpheader->ptype) == 0x0800)
		{
			printf("Sender MAC: ");
			for (i = 0; i < 6; ++i)
			{
				printf("%02x:", arpheader->sha[i]);
			}
			printf("\nSender IP: ");
			for (i = 0; i < 4; ++i)
			{
				printf("%d:", arpheader->spa[i]);
			}
			printf("\nTarget MAC: ");
			for (i = 0; i < 6; ++i)
			{
				printf("%02x:", arpheader->tha[i]);
			}
			printf("\nTarget IP: ");
			for (i = 0; i < 4; ++i)
			{
				printf("%d:", arpheader->tpa[i]);
			}
		}
		printf("\n\n");

	}

	pcap_close(dev);
}
