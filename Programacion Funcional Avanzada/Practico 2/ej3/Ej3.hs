module Ej3 (encode, decode) where

import Data.Word
import Data.Char

-- http://hackage.haskell.org/package/base-4.12.0.0/docs/Data-Char.html

encode :: String -> String -> IO ()
encode inF outF = do
  aux <- readFile inF
  writeFile outF $ procesar aux

decode :: String -> String -> IO ()
decode inF outF = do
  aux <- readFile inF
  writeFile outF $ deprocesar aux

procesar :: String -> String
procesar =  reverse . (map wordToChar ) . (zipWith (flip encodeWord) select). (map charToWord ) . concat . (map f ) . (filter isAlphaNum)
  where
    f c
        | isDigit c = spell c
        | isUpper c = [toLower c]
        | otherwise = [id c]


deprocesar :: String -> String
deprocesar =  (map wordToChar ) . (zipWith (flip decodeWord) select). (map charToWord ). reverse

select :: [Int]
select = concat $ repeat [0, 1, 2]

spell :: Char -> String
spell c = case c of
   '0' -> "cero"
   '1' -> "uno"
   '2' -> "dos"
   '3' -> "tres"
   '4' -> "cuatro"
   '5' -> "cinco"
   '6' -> "seis"
   '7' -> "siete"
   '8' -> "ocho"
   '9' -> "nueve"

charToWord :: Char -> Word8
charToWord = toEnum . fromEnum

wordToChar :: Word8 -> Char
wordToChar = toEnum . fromEnum

intToWord :: Int -> Word8
intToWord = toEnum . fromEnum

encodeWord :: Word8 -> Int -> Word8
encodeWord c p = mod (c + intToWord p) maxBound

decodeWord :: Word8 -> Int -> Word8
decodeWord c p = mod (c - intToWord p) maxBound
