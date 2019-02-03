module Ej2 where

data Tree a = Leaf a | Bin (Tree a) (Tree a)

unfoldTree :: (b -> Either a (b, b)) -> b -> Tree a
unfoldTree next s = case next s of
                         Left a     -> Leaf a
                         Right (b1, b2) -> Bin (unfoldTree next b1) (unfoldTree next b2)


toList :: Tree a -> [a]
toList (Leaf a) = [a]
toList (Bin a1 a2) = (toList a1) ++ (toList a2)

toString :: Tree Int -> String
toString (Leaf a) = show a
toString (Bin a1 a2) = "["++(toString a1)++"] [" ++ (toString a2)++"]"


replicateTree :: Int -> b -> Tree b
replicateTree n x = unfoldTree next n
                  where next 1  =  Left x
                        next n' = Right(div n' 2 + mod n' 2, div n' 2)


fromToTree :: Int -> Int -> Tree Int
fromToTree b e = unfoldTree next (b, e)
            where next (b', e') | b' == e'  = Left b'
                                | otherwise = Right((b', b' + (div (e'-b') 2) + d),(1 - d + b' + (div (e'-b') 2), e'))
                                              where d = if b' > e' then 1 else 0


mapTree :: (a -> b) -> Tree a -> Tree b
mapTree f = unfoldTree gMap
         where gMap (Leaf a) = Left (f a)
               gMap (Bin a1 a2) = Right ( a1,  a2)

