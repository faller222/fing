compiler.exe test1err > test1err.err
compiler.exe test2err > test2err.err
compiler.exe test3err > test3err.err
compiler.exe test4err > test4err.err
compiler.exe test5err > test5err.err
compiler.exe test6err > test6err.err
compiler.exe test7err > test7err.err
compiler.exe test8err > test8err.err
compiler.exe test9err > test9err.err
compiler.exe test10err > test10err.err

compiler.exe ejemplo1 > ejemplo1.err
compiler.exe ejemplo2 > ejemplo2.err
compiler.exe ejemplo3 > ejemplo3.err
compiler.exe ejemplo4 > ejemplo4.err
compiler.exe ejemplo5 > ejemplo5.err
compiler.exe ejemplo6 > ejemplo6.err
compiler.exe ejemplo7 > ejemplo7.err

compiler.exe test1
compiler.exe test2 
compiler.exe test3
compiler.exe test4
compiler.exe test5
compiler.exe test6
compiler.exe test7
compiler.exe test8
compiler.exe test9
compiler.exe test10


diff -w ejemplo1.c out/ejemplo1.c
diff -w ejemplo2.c out/ejemplo2.c
diff -w ejemplo3.c out/ejemplo3.c
diff -w ejemplo4.err out/ejemplo4.err
diff -w ejemplo5.err out/ejemplo5.err
diff -w ejemplo6.err out/ejemplo6.err
diff -w ejemplo7.err out/ejemplo7.err

diff -w test1.c out/test1.c
diff -w test2.c out/test2.c
diff -w test3.c out/test3.c
diff -w test4.c out/test4.c
diff -w test5.c out/test5.c
diff -w test6.c out/test6.c
diff -w test7.c out/test7.c
diff -w test8.c out/test8.c
diff -w test9.c out/test9.c
diff -w test10.c out/test10.c

diff -w test1err.err out/test1err.err
diff -w test2err.err out/test2err.err
diff -w test3err.err out/test3err.err
diff -w test4err.err out/test4err.err
diff -w test5err.err out/test5err.err
diff -w test6err.err out/test6err.err
diff -w test7err.err out/test7err.err
diff -w test8err.err out/test8err.err
diff -w test9err.err out/test9err.err
diff -w test10err.err out/test10err.err


