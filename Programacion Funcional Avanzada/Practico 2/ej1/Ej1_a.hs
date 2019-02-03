module Ej1_a where
-- | Original

import Prelude hiding (takeWhile,
                       zipWith)

takeWhile :: (a -> Bool ) -> [a] -> [a]
takeWhile p [] = []
takeWhile p (x : xs) | p x = x : takeWhile p xs
                     | otherwise = []

zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith f [] ys = []
zipWith f (x : xs) [] = []
zipWith f (x : xs) (y : ys) = f x y : zipWith f xs ys

tails :: [a ] -> [[a]]
tails [] = [[]]
tails ys@(x : xs) = ys : tails xs

evens :: [a] -> [a]
evens [] = []
evens [x] = []
evens (x : y : xs) = y : evens xs


