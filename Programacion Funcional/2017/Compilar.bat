echo off

if exist Compiler.exe (
    del Compiler.exe
    del *.hi
    del *.o
)

ghc --make Compiler

ejemplos.bat
