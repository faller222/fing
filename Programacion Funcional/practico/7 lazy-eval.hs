square x = x * x

inf :: Int
inf = 1 + inf

three :: Int -> Int
three x = 3

-- Determinar si dos arboles contienen los mismos valores en las hojas y en el mismo orden.

data Btree a = Leaf a | Fork (Btree a) (Btree a)
   deriving Show

t1 = Fork (Fork (Leaf 3) (Leaf 5)) (Fork (Leaf 6) (Fork (Leaf 8) (Leaf 9)))
t2 = Fork (Leaf 4)
          (Fork (Fork (Leaf 5) (Leaf 6))
                (Fork (Leaf 8) (Leaf 9)))

eqleaves :: Ord a => Btree a -> Btree a -> Bool
eqleaves t t' = leaves t == leaves t'

leaves (Leaf a)    = [a]
leaves (Fork l r)  = leaves l ++ leaves r

-- version para lenguaje estricto

eqleaves' t t' = comparestacks [t] [t']

comparestacks [] []               = True
comparestacks [] (t:ts)           = False
comparestacks (t:ts) []           = False
comparestacks (Fork l r:ts) ts'   = comparestacks (l:r:ts) ts'
comparestacks ts (Fork l r:ts')   = comparestacks ts (l:r:ts')
comparestacks (Leaf a:ts)  (Leaf b:ts')
                           | a == b     = comparestacks ts ts'
                           | otherwise  = False

-- funciones sobre listas infinitas

from n = iterate (+1) n

nats = iterate (+1) 0

fromTo m n = takeWhile (<= n) (from m)

pot2 = iterate (*2) 1

digits = reverse . map (`mod` 10) . takeWhile (/= 0) . iterate (`div` 10)

group n = map (take n) . takeWhile (not . null) . iterate (drop n)

unfold h p t = map h . takeWhile p . iterate t

primos = criba [2..]
criba (p:xs) = p : criba [x | x <- xs, x `mod` p /= 0]

ones = 1 : ones

nats' = 0: zipWith (+) ones nats'

factorials = 1 : zipWith (*) [1..] factorials  

fact 0 = 1
fact n = n * fact (n-1)

factorials' = map fact nats

-- arboles infinitos

btree1 = Fork (Leaf 5) btree1

btree2 = Fork (Fork btree2 (Leaf 2)) (Fork (Leaf 3) btree1)

leftmost (Leaf x) = x
leftmost (Fork l r) = leftmost l

ejemplo1 = leftmost btree1

ejemplo2 = leftmost btree2

ejemplo3 = take 5 $ map (*2) $ leaves btree1

ejemplo4 = take 5 $ map (*2) $ leaves btree2




