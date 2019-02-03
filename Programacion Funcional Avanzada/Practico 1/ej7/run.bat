ghc --make -prof -fprof-auto -rtsopts a1.hs
ghc --make -prof -fprof-auto -rtsopts a2.hs
ghc --make -prof -fprof-auto -rtsopts b1.hs
ghc --make -prof -fprof-auto -rtsopts b2.hs
ghc --make -prof -fprof-auto -rtsopts c1.hs
ghc --make -prof -fprof-auto -rtsopts c2.hs

a1.exe +RTS -p -K500M
a1.exe +RTS -hc -K500M
a2.exe +RTS -p -K500M
a2.exe +RTS -hc -K500M
b1.exe +RTS -p -K500M
b1.exe +RTS -hc -K500M
b2.exe +RTS -p -K500M
b2.exe +RTS -hc -K500M
c1.exe +RTS -p -K500M
c1.exe +RTS -hc -K500M
c2.exe +RTS -p -K500M
c2.exe +RTS -hc -K500M
