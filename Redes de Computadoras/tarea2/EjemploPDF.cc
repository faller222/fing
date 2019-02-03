
#include “opencv2/opencv.hpp”

Mat frame;                  //array que representa los frames del video
vector<uchar> encoded;      //vector para almacenar el frame codificado en jpeg
VideoCapture cap(video.mp4);//Obtener el video desde archivo.

//Crea una nueva ventana con nombre servidor
namedWindow("servidor", CV_WINDOW_AUTOSIZE);

//Obtener un frame del archivo y mostrarlo en pantalla
cap >> frame;                   //obtener nuevo frame
imshow("servidor", frame);      //muestra la imagen en la ventana servidor
WaitKey(1000/30);               //intervalo entre frames en ms

//Codificar la imagen en jpg para ser transmitida:
vector <int> compression_params;
compression_params.push_back(CV_IMWRITE_JPEG_QUALITY);
compression_params.push_back(80);

imencode(".jpg", frame, encoded, compression_params);
//la imagen codificada queda en encoded



//Decodificar y mostrar la imagen:
namedWindow("cliente", CV_WINDOW_AUTOSIZE);
//matriz con la imagen recibida.
//size es el tamaño de los datos recibidos
//en buffer se encuentran los datos recibidos
Mat rawData = Mat(1, size, CV_8UC1, buffer);
Mat frame = imdecode(rawData, CV_LOAD_IMAGE_COLOR);//decodifica la imagen

imshow("cliente", frame);//muestra la imagen en la ventana cliente
waitKey(1);