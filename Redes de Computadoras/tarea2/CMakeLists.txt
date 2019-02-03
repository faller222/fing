cmake_minimum_required(VERSION 3.5)

set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
SET(CMAKE_CXX_FLAGS ${CMAKE_CXX_FLAGS} "-Wl,--no-as-needed -std=c++11 -pthread")


project( obligatorio2 )
find_package (Threads REQUIRED )
find_package( OpenCV REQUIRED )

add_executable( servidor servidor.cc)
target_link_libraries(servidor ${OpenCV_LIBS} )


add_executable( cliente cliente.cc)
target_link_libraries( cliente ${OpenCV_LIBS} )

target_link_libraries (servidor ${CMAKE_THREAD_LIBS_INIT})
target_link_libraries (cliente ${CMAKE_THREAD_LIBS_INIT})
