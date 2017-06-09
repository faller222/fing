echo off
cls
cls

if exist Compiler.exe (
    del Compiler.exe
    del *.hi
    del *.o
)

ghc --make Compiler

if exist Compiler.exe (
    echo ejemplo1
    Compiler.exe ejemplo1

    echo ejemplo2
    Compiler.exe ejemplo2

    echo ejemplo3
    Compiler.exe ejemplo3

    echo ejemplo4
    Compiler.exe ejemplo4

    echo ejemplo5
    Compiler.exe ejemplo5

    echo ejemplo6
    Compiler.exe ejemplo6 > ejemplo6.err

    echo ejemplo7
    Compiler.exe ejemplo7 > ejemplo7.err

    echo ejemplo8
    Compiler.exe ejemplo8 > ejemplo8.err

    echo ejemplo9
    Compiler.exe ejemplo9 > ejemplo9.err
)

