module Main where

import Ej3 as S
import Ej3BS as BS


s1 :: IO ()
s1 = S.encode "C:\\Users\\faller222\\Desktop\\PFA\\Practico 2\\ej3\\text" "C:\\Users\\faller222\\funcional.txt"

s2 :: IO ()
s2 = S.decode "C:\\Users\\faller222\\funcional.txt" "C:\\Users\\faller222\\funcionaldeco.txt"

bs1 :: IO ()
bs1 = BS.encode "C:\\Users\\faller222\\Desktop\\PFA\\Practico 2\\ej3\\text" "C:\\Users\\faller222\\funcionalBS.txt"

bs2 :: IO ()
bs2 = BS.decode "C:\\Users\\faller222\\funcionalBS.txt" "C:\\Users\\faller222\\funcionaldecoBS.txt"


main :: IO ()
main = bs1
