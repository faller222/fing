#include <string.h>

#include <netinet/in.h>
#include <netdb.h>



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

#define PORT "55579"
#define HOST "localhost"
#define MAX_MSG_SIZE 256



int main(void) {
   
        int client_socket = socket(AF_INET, SOCK_DGRAM, 0);
        struct addrinfo hints, *res;
        hints.ai_family = AF_INET;
        hints.ai_socktype = SOCK_DGRAM;
        getaddrinfo(HOST, PORT, &hints, &res);

        Mat frame;
        vector < uchar > encoded;
        VideoCapture cap("video_prueba.mp4"); 
        while (1) {
            cap >> frame;
            if(frame.empty()) break;
            vector < int > compression_params;
            compression_params.push_back(CV_IMWRITE_JPEG_QUALITY);
            compression_params.push_back(80);

            imencode(".jpg", frame, encoded, compression_params);
            imshow("cliente-sd", frame);

            sendto(client_socket, & encoded[0], encoded.size(), 0, res->ai_addr, res->ai_addrlen);

            waitKey(1000/30);
        }

    close(client_socket);
}
