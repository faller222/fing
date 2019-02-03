module Main where

import Test.QuickCheck
import Tree


instance Arbitrary a => Arbitrary (Tree a)  where
  arbitrary = fmap fromList arbitrary


fromList :: [a] -> Tree a
fromList []     = Empty
fromList (x:xs) = Node (fromList lhalf) x (fromList rhalf)
    where (lhalf, rhalf) = splitAt (length xs `div` 2) xs

main :: IO ()
--main = quickCheck (propiedadC :: Eq a => Tree a -> Bool )
main = quickCheck propiedadLema

--quickCheck (\t -> collect (size t `div` 10) (propiedadTeo t))
--quickCheck (\xs xy -> collect (length xs `div` 10) (propiedadLema xs xy))
--quickCheck (\t -> collect (size t `div` 10) (propiedadA t))
--quickCheck (\t -> collect (size t `div` 10) (propiedadB t))
--quickCheck (\t -> collect (size t `div` 10) (propiedadC t))

