data Arbol a = Vacio | Nodo (Arbol a) a (Arbol a)

genera = fst $ generaAux 0
    where generaAux n = let (l , n1 ) = generaAux (n + 1)
                            (r , n2) = generaAux n1
                        in (Nodo l n r , n2)

recorreL (Nodo l x _ ) = x : recorreL l
recorreL Vacio = []

recorreR (Nodo _ x r ) = x : recorreR r
recorreR Vacio = []

recorre (Nodo l x r ) = recorre l ++ [x] ++ recorre r
recorre Vacio = []

take1 _ [] = []
take1 n (x : xs) | n > 0 = x : take1 (n - 1) xs
                 | otherwise = []
main = do print (take1 5 . tail . recorreL $ genera)
          print (head $ zip (recorreL genera) (recorreR genera))
