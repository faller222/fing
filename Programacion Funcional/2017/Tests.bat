echo off

if NOT exist Compiler.exe (
    ghc --make Compiler
)


if exist Compiler.exe (
    echo test1
    Compiler.exe tests/test1

    echo test2
    Compiler.exe tests/test2

    echo test3
    Compiler.exe tests/test3

    echo test4
    Compiler.exe tests/test4

    echo test5
    Compiler.exe tests/test5

    echo test1err
      Compiler.exe tests/test1err > tests/test1err.err

    echo test2err
    Compiler.exe tests/test2err > tests/test2err.err

    echo test3err
    Compiler.exe tests/test3err > tests/test3err.err

    echo test4err
    Compiler.exe tests/test4err > tests/test4err.err

    echo test5err
    Compiler.exe tests/test5err > tests/test5err.err
)

