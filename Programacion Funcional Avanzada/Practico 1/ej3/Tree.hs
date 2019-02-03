
data Tree a = Empty | Node (Tree a) a (Tree a)

deriving Show

size :: Tree a -> Int
size Empty = 0
size (Node l r ) = size l + 1 + size r

length :: [a] -> Int
length [] = 0
length (x : xs) = 1 + length xs

flatten :: Tree a -> [a]
flatten Empty = []
flatten (Node l a r ) = flatten l ++ [a] ++ flatten r

(++) :: [a] -> [a] -> [a]
[] ++ ys = ys
(x : xs) ++ ys = x : (xs ++ ys)
