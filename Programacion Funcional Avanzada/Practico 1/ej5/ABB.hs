module ABB (ABB(), empty, insert, member, height ) where


data ABB a = Empty | Node (ABB a) a (ABB a)

empty :: ABB a
empty = Empty

insert :: Ord a => a -> ABB a -> ABB a
insert x Empty = Node (Empty) x (Empty)
insert x (Node r a l) | x > a   = Node (insert x r) a l
                    | otherwise = Node r a (insert x l)

member :: Ord a => a -> ABB a -> Bool
member x Empty = False
member x (Node r a l) = a==x || member x l || member x r

height :: ABB a -> Int
height Empty = 0
height (Node r a l) = 1 + (max  (height r)  (height l))