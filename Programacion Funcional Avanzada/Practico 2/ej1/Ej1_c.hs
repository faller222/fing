module Ej1_c where
 -- | En terminos de Repr


import StreamFusion(unfoldr,
                    Repr(..),
                    fromRepr)

import Prelude hiding (takeWhile,
                       zipWith)


-- | #########################################################


takeWhile :: (a -> Bool ) -> [a] -> [a]
takeWhile p l = fromRepr $  takeWhileR p l

takeWhileR :: (a -> Bool ) -> [a] -> Repr a
takeWhileR p = Repr next
                   where next []     = Nothing
                         next (x:xs) = if p x then Just (x,xs)
                                              else Nothing


zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith f l1 l2 = fromRepr $ zipWithR f l1 l2

zipWithR :: (a -> b -> c) -> [a] -> [b] -> Repr c
zipWithR f l1 l2 = Repr next (l1, l2)
                    where next ([], ys) = Nothing
                          next ((x : xs), []) = Nothing
                          next ((x : xs), (y : ys)) = Just (f x y , (xs, ys))

tails :: [a] -> [[a]]
tails  = fromRepr . tailsR

tailsR :: [a] -> Repr [a]
tailsR l = Repr next (Just l)
        where next (Nothing) = Nothing
              next (Just []) = Just([],(Nothing))
              next (Just ys@(x : xs)) = Just(ys,(Just xs))


evens :: [a] -> [a]
evens  = fromRepr . evensR

evensR :: [a] -> Repr a
evensR = Repr next
         where next [] = Nothing
               next [x] = Nothing
               next (x : y : xs) = Just(y, xs)
