import ABB

import Test.QuickCheck

ordered :: Ord a => [a] -> Bool
ordered [] = True
ordered [x] = True
ordered (x : y : ys) = x <= y && ordered (y : ys)

fromList :: Ord a => [a] -> ABB a
fromList [] = empty
fromList (x:xs) = insert x $ fromList xs

propiedadOrdenada :: Ord a => [a] -> Property
propiedadOrdenada xs = ordered xs ==> length xs == height (fromList (xs))



main :: IO ()
main = quickCheck propiedadOrdenada
--quickCheck (\xs -> collect (length xs `div` 10) (propiedadOrdenada xs))