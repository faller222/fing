module Ej1_d where
-- | En terminos de Stream

import StreamFusion(Stream(..),
                    Step(..),
                    unstream,
                    stream)

import Prelude hiding (takeWhile,
                       zipWith)


takeWhile :: (a -> Bool ) -> [a] -> [a]
takeWhile p = unstream . takeWhileS p . stream

takeWhileS :: (a -> Bool ) -> Stream a -> Stream a
takeWhileS p  (Stream next s) = Stream next' s
          where next' s = case next s of
                             Done       -> Done
                             Skip s'    -> Skip s'
                             Yield x s' -> if p x then Yield x s'
                                                  else Done


zipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith f l1 l2 = unstream $ zipWithS f (stream l1) (stream l2)

zipWithS :: (a -> b -> c) -> Stream a -> Stream b -> Stream c
zipWithS f (Stream next1 s1) (Stream next2 s2) = Stream next (Left (s1,s2))
          where
            next (Left (s1,s2))  = case next1 s1 of
                                Done        -> Done
                                Skip s1'    -> Skip (Left (s1',s2))
                                Yield x s1' -> Skip (Right (x, s1', s2))
            next (Right (a,s1',s2)) = case next2 s2 of
                                Done        -> Done
                                Skip s2'    -> Skip (Right (a,s1', s2'))
                                Yield x s2' -> Yield (f a x) (Left (s1',s2'))


tails :: [a] -> [[a]]
tails  = unstream . tailsS . stream

tailsS :: Stream a -> Stream [a]
tailsS (Stream next1 s1) = Stream next (Just (s1))
         where next (Nothing) = Done
               next (Just (s1)) = case next1 s1 of
                                 Done        -> Yield [] (Nothing)
                                 Skip s''    -> Skip (Just(s''))
                                 Yield x s'' -> Yield (unstream (Stream next1 s1)) (Just (s''))


evens :: [a] -> [a]
evens  = unstream . evensS . stream

evensS :: Stream a -> Stream a
evensS (Stream next s) = Stream next' (Left s)
          where
           next' (Left (s))  = case next s of
                                  Done       -> Done
                                  Skip s'    -> Skip (Left (s'))
                                  Yield x s' -> Skip (Right (s'))
           next' (Right (s)) = case next s of
                              Done       -> Done
                              Skip s'    -> Skip (Right (s'))
                              Yield x s' -> Yield x (Left (s'))
