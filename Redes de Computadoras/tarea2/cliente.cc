
//https://www.geeksforgeeks.org/socket-programming-cc/
//https://www.geeksforgeeks.org/udp-server-client-implementation-c/

#include <opencv2/opencv.hpp>
#include <cv.hpp>

#include <unistd.h>
#include <stdio.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>
#include <iostream>
#include <thread>
#include <arpa/inet.h>
#include <mutex>
#include <chrono>
#include <netinet/in.h>
#include <netdb.h>

#include <sys/socket.h>
#include <sys/types.h>

#include <vector>


#define SIZE_RCV 65536

using namespace cv;
using namespace std;

int pack_recv = 0;

int openUDP(char *);

void reciveUDP(int);

int connectServer(char const *, int);

void sendServer(int, char *);

int main(int argc, char *argv[]) {
    char *hostName;
    int server_port;

    if (argc > 2) {
        hostName = argv[1];
        server_port = stoi(argv[2]);
    } else {
        printf("Los parametros de entrada deben ser IP_SERVIDOR PUERTO_SERVIDOR \n");
        return 0;
    }

    char str[80];
    int sockUdp = openUDP(str);
    char str1[20];
    strcpy(str1, "Start:");
    strcat(str1, str);

    thread serverUDP(reciveUDP, sockUdp);

    int sockServer = connectServer(hostName, server_port);

    string opcion;
    do {
        std::cout << "Menu:" << "\n";
        std::cout << "\tA) - Start \n";
        std::cout << "\tB) - Pause \n";
        std::cout << "\tC) - Stop \n";
        std::cout << "\tS) - Salir \n";

        cout << "\nOpcion: ";
        cin >> opcion;
        switch (opcion[0]) {
            case 'A':
                sendServer(sockServer, const_cast<char *>(str1));
                break;
            case 'B':
                sendServer(sockServer, const_cast<char *>("Pause"));
                break;
            case 'C':
                sendServer(sockServer, const_cast<char *>("Stop"));
                break;
            case 'S':
                sendServer(sockServer, const_cast<char *>("Quit"));
                serverUDP.detach();
                break;
            default:
                cout << "COMANDO DESCONOCIDO \n";
        }
    } while (opcion[0] != 'S');
    if (serverUDP.joinable()) {
        serverUDP.join();
    }
    std::cout << "[Fin de la Transmision]" << "\n";
    printf(" Paquetes recividos %i\n", pack_recv);
    return 0;
}

int connectServer(char const *host, int server_port) {
    int sock = 0;
    struct sockaddr_in serv_addr{};

    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        printf("\n Socket creation error \n");
        return -1;
    }

    memset(&serv_addr, 0, sizeof(serv_addr));

    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(server_port);

    // Convert IPv4 and IPv6 addresses from text to binary form
    if (inet_pton(AF_INET, host, &serv_addr.sin_addr) <= 0) {
        printf("\nInvalid address/ Address not supported \n");
        return -1;
    }

    if (connect(sock, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
        printf("\nConnection Failed \n");
        return -1;
    }

    return sock;
}

void sendServer(int sock, char *opt) {
    send(sock, opt, strlen(opt), 0);
}


int openUDP(char *str) {
    printf("[Iniciando Conexion UDP]\n");

    int server_socket = socket(PF_INET, SOCK_DGRAM, 0);
    struct sockaddr_in server_addr{};
    socklen_t server_addr_size = sizeof server_addr;
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(0); //obtener puerto aleatorio
    bind(server_socket, (struct sockaddr *) &server_addr, server_addr_size);

    //imprime el puerto obtenido
    getsockname(server_socket, (struct sockaddr *) &server_addr, &server_addr_size);

    int str_len = sprintf(str, "%u", ntohs(server_addr.sin_port));
    printf("The selected port is %s.\n", str);

    printf("[Conexion UDP Lista]\n");
    return server_socket;
}

void reciveUDP(int sockfd) {

    char data_rcv[SIZE_RCV];
    int received_data_size;
    struct sockaddr_in remote_addr{};

    socklen_t remote_addr_size = sizeof remote_addr;
    while (true) {

        char *aux_encode = new char[SIZE_RCV];

        received_data_size = recvfrom(sockfd, data_rcv, SIZE_RCV, 0, (struct sockaddr *) &remote_addr, &remote_addr_size);
        pack_recv++;


        memcpy(&aux_encode[0], data_rcv, received_data_size);

        if(received_data_size==0){
            continue;
        }

        Mat rawData = Mat(1, received_data_size, CV_8UC1, data_rcv);
        Mat frame = imdecode(rawData, CV_LOAD_IMAGE_COLOR);

        if (!frame.empty()) {
            imshow("video-cliente", frame);
            waitKey(1);
        }
        delete[] aux_encode;

    }
    //close(server_socket);

}