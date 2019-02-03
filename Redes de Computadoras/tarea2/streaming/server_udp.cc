#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <stdio.h>

#include <iostream>
#include <arpa/inet.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "opencv2/opencv.hpp"

using namespace std;
using namespace cv;

#define PORT 8890
#define MY_IP "127.0.0.1"
#define MAX_MSG_SIZE 256

#define SIZE_RCV 65536
//#define SIZE_RCV 32768 con este valor se ve la imagen distorcionada de a ratos



int main(void) {


        int server_socket = socket(PF_INET, SOCK_DGRAM, 0);
        struct sockaddr_in server_addr;
        socklen_t server_addr_size = sizeof server_addr;
        server_addr.sin_family = AF_INET;
        server_addr.sin_port = htons(0); //obtener puerto aleatorio
        server_addr.sin_addr.s_addr = inet_addr(MY_IP);
        bind(
            server_socket, 
            (struct sockaddr*)&server_addr, server_addr_size
        );

        //imprime el puerto obtenido
        getsockname(server_socket, (struct sockaddr*) &server_addr, &server_addr_size);
        printf("The selected port is %d.\n", ntohs(server_addr.sin_port));

        while (1) {
            char data_rcv[SIZE_RCV];
            int received_data_size;
            struct sockaddr_in remote_addr;
            socklen_t remote_addr_size = sizeof remote_addr;
            char * aux_encode = new char[SIZE_RCV];
            received_data_size = recvfrom(server_socket, data_rcv, SIZE_RCV, 0,(struct sockaddr*)&remote_addr, &remote_addr_size);
            memcpy( & aux_encode[0], data_rcv, received_data_size);
 
            Mat rawData = Mat(1, received_data_size, CV_8UC1, data_rcv);
            Mat frame = imdecode(rawData, CV_LOAD_IMAGE_COLOR);
            imshow("server-rcv", frame);
            waitKey(1);
        }

}