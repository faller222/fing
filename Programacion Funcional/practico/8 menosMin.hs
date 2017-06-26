-- Dada una lista no vacia calculamos el m?nimo elemento
-- y se lo restamos a cada elemento de la lista

-- Version inicial que calcula en dos pasadas, una para
-- calcular la lista y una segunda para la nueva lista

menosmin :: [Int] -> [Int]
menosmin xs = let m = minimo xs 
              in  restar xs m 
                    
minimo :: [Int] -> Int
minimo [x]    = x
minimo (x:xs) = min x (minimo xs)
                
restar :: [Int] -> Int -> [Int]
restar []     m = [] 
restar (x:xs) m = (x-m) : restar xs m


-- Solucion usando alto orden.

menosmin_ho xs = let (m,fres) = minrest_ho xs
                 in  fres m

minrest_ho [x]    = (x,\r -> [x - r])
minrest_ho (x:xs) = let (m,fres) = minrest_ho xs
                    in  (min x m, \r -> x - r : fres r) 


-- Version circular

menosmin_circ xs = let (m,res) = minrest_circ xs m
                   in  res

minrest_circ [x]    r = (x, [x - r])
minrest_circ (x:xs) r = let (m,res) = minrest_circ xs r
                        in  (min x m, x - r : res) 
