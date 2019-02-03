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


#define SERVER_PORT 8080

using namespace cv;
using namespace std;


int openTCP(int);

void atenderSocket(int, char *);

void connectClient(char *, int &, const string &);

int main(int argc, char *argv[]) {

    //Puerto por defecto el 8080
    int server_port = SERVER_PORT;

    if (argc > 1) {
        server_port = stoi(argv[1]);
    }

    int new_socket;
    struct sockaddr_in cliaddr;
    int addrlen = sizeof(cliaddr);
    int server_fd = openTCP(server_port);

    vector<std::thread> conexiones;

    while (true) {
        if ((new_socket = accept(server_fd, (struct sockaddr *) &cliaddr, (socklen_t *) &addrlen)) < 0) {
            perror("accept");
            exit(EXIT_FAILURE);
        }

        struct in_addr ipAddr = cliaddr.sin_addr;
        char addr_str[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, &ipAddr, addr_str, INET_ADDRSTRLEN);

        conexiones.emplace_back(atenderSocket, new_socket, addr_str);
    }

}

int openTCP(int server_port) {
    printf("[Iniciando servidor]\n");

    struct sockaddr_in servaddr{};
    int server_fd;

    // Creating socket file descriptor
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }


    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(server_port);

    if (bind(server_fd, (struct sockaddr *) &servaddr, sizeof(servaddr)) < 0) {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }

    if (listen(server_fd, 3) < 0) {
        perror("listen");
        exit(EXIT_FAILURE);
    }

    printf("[Esperando Conexiones]\n");
    return server_fd;
}

void atenderSocket(int new_socket, char *addr) {

    printf("- Nueva conexion (%d) - %s \n", new_socket, addr);
    //TODO: ver como terminar la conexion por inactividad
    char buffer[1024] = {0};
    std::mutex mu;

    thread send1;
    int state = 0;
    for (;;) {

        memset(buffer, 0, 1024);
        if (read(new_socket, buffer, 1024) < 1) break;

        printf("(%d) - %s \n", new_socket, buffer);

        string str(buffer);
        int len_str = str.size();
        int len_port;
        string port_udp;

        string first = str.substr(0, 5);

        if (state == 5) {//Esta STOPPED (termino viedo);
            state = 0;
            send1.join();
        }

        if (first == "Start") {
            switch (state) {
                case 0:  //Nunca dio START
                case 3: //Esta STOPPED;
                    len_port = len_str - 6;
                    port_udp = str.substr(6, len_port);
                    send1 = thread(connectClient, addr, std::ref(state), port_udp);
                    break;
                case 1:
                case 2:
                case 4: //Esta Quit;
                    break;

                default:
                    cout << "ESTADO DESCONOCIDO \n";
            }
            state = 1;
        } else if (str == "Pause") {
            switch (state) {
                case 0: //Nunca dio START
                    break;
                case 1:
                    printf("%s \n", "PAUSAR");
                    state = 2;
                    break;
                case 2:
                    printf("%s \n", "RESUMIR");
                    state = 1;
                    break;
                case 3: //Esta STOPPED;
                case 4: //Esta Quit;
                    break;
                default:
                    cout << "ESTADO DESCONOCIDO \n";
            }
        } else if (str == "Stop") {
            switch (state) {
                case 0:  //Nunca dio START
                    break;
                case 1:
                case 2:
                    printf("%s \n", "PARAR");
                    state = 3;
                    send1.join();
                    break;
                case 3: //Esta STOPPED;
                case 4: //Esta Quit;
                    break;
                default:
                    cout << "ESTADO DESCONOCIDO \n";
            }
        } else if (str == "Quit") {
            printf("%s \n", "Salir");
            state = 4;
            break;
        }


    }

    printf("- Fin conexion (%d) - %s \n", new_socket, addr);
    if (send1.joinable()) {
        send1.join();
    }
}


void connectClient(char *addr, int &state, const string &port) {

    int client_socket = socket(AF_INET, SOCK_DGRAM, 0);
    struct addrinfo hints{}, *res;
    hints.ai_family = AF_INET;
    hints.ai_socktype = SOCK_DGRAM;


    getaddrinfo(addr, port.c_str(), &hints, &res);

    int sockfd;

    // Creating socket file descriptor
    if ((sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 1) {
        perror("socket creation failed");
        exit(EXIT_FAILURE);
    }


    Mat frame;
    vector<uchar> encoded;
//    VideoCapture cap("video/videoShort.mp4");
    VideoCapture cap("video.mp4");
    int pack_enviados = 0;
    while (true) {
        if (state == 3 || state == 4){
            break;
        }
        if (state == 2) {//Esta Pausado, espero medio segundo
            usleep(500000);
            continue;
        }
        if (state == 1) {
            cap >> frame;
            if (frame.empty()) {
                state = 5;
                break;
            }
            vector<int> compression_params;
            compression_params.push_back(CV_IMWRITE_JPEG_QUALITY);
            compression_params.push_back(80);

            imencode(".jpg", frame, encoded, compression_params);

            sendto(sockfd, &encoded[0], encoded.size(), 0, res->ai_addr, res->ai_addrlen);
            pack_enviados++;

            usleep(25000);
        }
    }
    printf("Paquetes enviados %i\n", pack_enviados);

    cap.release();


    if (state == 4)
        close(sockfd);
}