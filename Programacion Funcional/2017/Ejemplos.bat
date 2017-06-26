echo off

if NOT exist Compiler.exe (
    ghc --make Compiler
)


if exist Compiler.exe (
    echo ejemplo1
    Compiler.exe ejemplos/ejemplo1

    echo ejemplo2
    Compiler.exe ejemplos/ejemplo2

    echo ejemplo3
    Compiler.exe ejemplos/ejemplo3

    echo ejemplo4
    Compiler.exe ejemplos/ejemplo4

    echo ejemplo5
    Compiler.exe ejemplos/ejemplo5

    echo ejemplo6
    Compiler.exe ejemplos/ejemplo6 > ejemplos/ejemplo6.err

    echo ejemplo7
    Compiler.exe ejemplos/ejemplo7 > ejemplos/ejemplo7.err

    echo ejemplo8
    Compiler.exe ejemplos/ejemplo8 > ejemplos/ejemplo8.err

    echo ejemplo9
    Compiler.exe ejemplos/ejemplo9 > ejemplos/ejemplo9.err

    tests.bat
)


