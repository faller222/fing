module Tree (Tree (Empty, Node),propiedadLema,propiedadTeo,propiedadA, propiedadB, propiedadC)where

data Tree a = Empty | Node (Tree a) a (Tree a)
    deriving (Show, Eq)


size :: Tree a -> Int
size Empty = 0
size (Node l _ r ) = size l + 1 + size r

flatten :: Tree a -> [a]
flatten Empty = []
flatten (Node l a r ) = flatten l ++ [a] ++ flatten r

mirror :: Tree a -> Tree a
mirror Empty = Empty
mirror (Node l v r ) = (Node (mirror r) v (mirror l))


propiedadLema :: [a] -> [a] -> Bool
propiedadLema xs ys = length (xs ++ ys) == length xs + length ys

propiedadTeo :: Tree a -> Bool
propiedadTeo t = (length( flatten (t))) == (size (t))

propiedadA ::Eq a => Tree a -> Bool
propiedadA t = (flatten ( mirror (t))) == (reverse ( flatten (t)))

propiedadB ::Eq a => Tree a -> Bool
propiedadB t = (mirror ( mirror (t))) == (t)

propiedadC ::Eq a => Tree a -> Bool
propiedadC t = (size (mirror t)) == (size (t))

--main :: IO ()
--main = quickCheck (propiedadC :: Tree a->Bool )

