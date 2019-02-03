-- {-# LANGUAGE ExistentialQuantification, GADTs #-}
module Ej3BS (encode, decode) where

import Data.Word
import Data.Char

import qualified Data.ByteString.Lazy.Char8 as BC
import qualified Data.ByteString.Lazy as B

encode :: String -> String -> IO ()
encode s d = do
  x <- BC.readFile s
  BC.writeFile d $ procesar x

decode :: String -> String -> IO ()
decode s d = do
  x <- BC.readFile s
  BC.writeFile d $ deprocesar x

procesar :: B.ByteString -> B.ByteString
procesar =  B.reverse . B.pack . ( B.zipWith (flip encodeWord) sec). B.concatMap f . B.filter (isAlphaNum8)
   where
     f :: Word8  -> B.ByteString
     f c
      | isDigit8 c = num c
      | isUpper8 c = BC.pack [toLower8 c]
      | otherwise = BC.pack [wordToChar c]

deprocesar :: B.ByteString -> B.ByteString
deprocesar = B.pack . ( B.zipWith (flip decodeWord) sec). B.reverse

select :: [Int]
select = concat $ repeat [0, 1, 2]

sec :: B.ByteString
sec = BC.pack $ map toEnum select

num :: Word8 -> B.ByteString
num c = case wordToChar c of
   '0' -> BC.pack "cero"
   '1' -> BC.pack "uno"
   '2' -> BC.pack "dos"
   '3' -> BC.pack "tres"
   '4' -> BC.pack "cuatro"
   '5' -> BC.pack "cinco"
   '6' -> BC.pack "seis"
   '7' -> BC.pack "siete"
   '8' -> BC.pack "ocho"
   '9' -> BC.pack "nueve"


wordToChar :: Word8 -> Char
wordToChar = toEnum . fromEnum

encodeWord :: Word8 -> Word8 -> Word8
encodeWord c p = mod (c + p) maxBound

decodeWord :: Word8 -> Word8 -> Word8
decodeWord c p = mod (c - p) maxBound


isUpper8 :: Word8 -> Bool
isUpper8 = (isUpper . wordToChar)

isDigit8 :: Word8 -> Bool
isDigit8 = (isDigit . wordToChar)

isAlphaNum8 :: Word8 -> Bool
isAlphaNum8 = (isAlphaNum . wordToChar)

toLower8 :: Word8 -> Char
toLower8 = (toLower . wordToChar)

