-- Funciones sobre listas

import Prelude hiding (init,last,splitAt,sum,and,map,filter)

m `divide` n = n `mod` m == 0

divisores n = [d | d <- [1..n], d `divide` n]

divisores' n = filter (`divide` n) [1..n]

mcd x y = maximum [d | d <- divisores x, d `divide` y]

mcd' x y = maximum . filter (`divide` y) . divisores $ x

primo n = divisores n == [1,n]

primo' n = (n > 1)
           &&
           ([d | d <- [2..intSqrt n], d `divide` n] == [])

intSqrt :: Integer -> Integer
intSqrt n = floor $ sqrt (fromInteger n)

factores n = filter (`divide` n) [1..n `div` 2]

perfecto n = sum (factores n) == n

init :: [a] -> [a]
init xs = take (length xs - 1) xs

last :: [a] -> a
last xs = head $ drop (length xs - 1) xs

splitAt :: Int -> [a] -> ([a],[a])
splitAt n xs = (take n xs, drop n xs)

trail :: Int -> String -> String
trail n = unlines . reverse . take n . reverse . lines
-- https://stackoverflow.com/questions/940382/haskell-difference-between-dot-and-dollar-sign
-- https://stackoverflow.com/questions/9142731/what-does-the-symbol-mean-in-haskell
presc :: Num a => [a] -> [a] -> a
presc xs ys = sum . map (uncurry (*)) $ zip xs ys

nondec :: Ord a => [a] -> Bool
nondec xs = and . map (uncurry (<=)) $ zip xs (tail xs)

-- Ejemplos foldr

sum :: Num a => [a] -> a
sum = foldr (+) 0

and :: [Bool] -> Bool
and = foldr (&&) True

map :: (a -> b) -> [a] -> [b]
map f = foldr (cons . f) []
  where cons x xs = x : xs

filter :: (a -> Bool) -> [a] -> [a]
filter p = foldr op []
  where op a r  | p a        = a : r
                | otherwise  = r
