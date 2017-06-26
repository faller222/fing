import Prelude hiding (map,filter,sum,concat,reverse)

factorial n = product [1..n]

fact :: Integer -> Integer
fact n
  | n == 0  = 1
  | n > 0   = n * fact (n - 1)

fact1 n
  | n == 0     = 1
  | otherwise  = n * fact1 (n - 1)

fact2 0  = 1
fact2 n  = n * fact2 (n-1)

fact3 n = factAux 1 n
  where
    factAux acc 0 = acc
    factAux acc n = factAux (acc*n) (n-1)  

{- recursion estructural -}

pot2 :: Integer -> Integer
pot2  0  = 1
pot2  n  = 2 * pot2 (n-1)

sumF :: (Integer -> Integer) -> Integer -> Integer
sumF f 0 = f 0
sumF f n = f n + sumF f (n-1)

mult :: Integer -> Integer -> Integer
mult m 0  = 0
mult m n  = m + mult m (n-1)

{- recursion general -}

fib :: Integer -> Integer
fib  0  = 0
fib  1  = 1
fib  n  = fib (n-1) + fib (n-2)

-- polinomios de Hermite
he 0 x  = 1
he 1 x  = 2 * x
he n x  = 2 * x * he (n-1) x - 2 * (n-1) * he (n-2) x

division :: Integer -> Integer -> Integer
division m n
   | m < n      = 0
   | otherwise  = 1 + division (m-n) n

resto :: Integer -> Integer -> Integer
resto m n
   | m < n      = m
   | otherwise  = resto (m-n) n

mcd :: Integer -> Integer -> Integer
mcd m n 
    | m == n  = m
    | m > n   = mcd (m-n) n
    | m < n   = mcd m (n-m)

fastmcd :: Integer -> Integer -> Integer
fastmcd m 0  = m
fastmcd m n  = fastmcd n (m `mod` n)      

{- recursion mutua -}

par :: Integer -> Bool
par 0  = True
par n  = impar (n-1)

impar :: Integer -> Bool
impar 0  = False
impar n  = par (n-1)

{- insertion sort -}

isort :: Ord a => [a] -> [a]
isort []      = []
isort (x:xs)  = insert x (isort xs)

insert :: Ord a => a -> [a] -> [a]
insert x []                  = [x]
insert x (y:ys) | x <= y     = x : y : ys
                | otherwise  = y : insert x ys


{- foldr -}

sum :: Num a => [a] -> a
sum = foldr (+) 0

all :: [Bool] -> Bool
all = foldr (&&) True

-- https://wiki.haskell.org/Anonymous_function
length :: [a] -> Int
length = foldr (\_ -> \y -> 1 + y) 0

concat :: [[a]] -> [a]
concat = foldr (++) []

map f = foldr (cons . f) []
   where cons x xs = x : xs

reverse :: [a] -> [a]
reverse = foldr snoc []
  where snoc x ys = ys ++ [x]

isort' :: Ord a => [a] -> [a]
isort' = foldr insert []

{- foldl -}

sum2 = sum' 0
  where
     sum' v []      = v
     sum' v (x:xs)  = sum' (v + x) xs

sum2' :: Num a => [a] -> a
sum2' = foldl (+) 0

rev = rev' []
  where
     rev' ys []      = ys
     rev' ys (x:xs)  = rev' (x : ys) xs

rev2 :: [a] -> [a]
rev2 = foldl (flip (:)) []
