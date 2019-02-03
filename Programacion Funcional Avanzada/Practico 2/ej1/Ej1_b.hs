module Ej1_b where
-- | En terminos de unfoldr

import Prelude hiding (takeWhile,
                       zipWith)

import StreamFusion(unfoldr)

-- | #########################################################

takeWhile :: (a -> Bool ) -> [a] -> [a]
takeWhile p = unfoldr takeUFR
                where takeUFR []     = Nothing
                      takeUFR (x:xs) = if p x then Just (x,xs)
                                              else Nothing


zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith f l1 l2 = unfoldr zipWithUFR (l1, l2)
                where zipWithUFR ([], ys) = Nothing
                      zipWithUFR ((x : xs), []) = Nothing
                      zipWithUFR ((x : xs), (y : ys)) = Just (f x y , (xs, ys))


tails :: [a] -> [[a]]
tails l = unfoldr tailsUFR (Just l)
            where tailsUFR (Nothing) = Nothing
                  tailsUFR (Just []) = Just([],(Nothing))
                  tailsUFR (Just ys@(x : xs)) = Just(ys,(Just xs))


evens :: [a] -> [a]
evens = unfoldr evensUFR
                 where evensUFR [] = Nothing
                       evensUFR [x] = Nothing
                       evensUFR (x : y : xs) = Just(y, xs)


