module Tabulacion where

import Data.Ix
import Data.Array

-- En este modulo se implementa la tecnica de tabulacion para varios
-- ejemplos usando tablas representadas por listas y por arrays.
-- La tecnica de tabulacion consiste en almacenar ciertos valores
-- en una tabla durante el desarrollo de la solucion a un problema.
-- Esta tecnica es muy usada en el contexto de programacion dinamica.
-- Nuestro uso de tabulacion se basa fuertemente en lazy evaluation,
-- dado que implementa el llenado de la tabla en forma circular. 

-- Correr las diferentes versiones de cada ejemplo para notar la diferencia
-- de performance que hay entre ellas, en particular entre la version recursiva
-- no tabulada y las correspondientes tabuladas. 


-- Tabla implementada con listas

-- Se carga un valor adicional de tipo i que representa un shift.

data Table i a = T [a] i

mkTable :: Ix i => (i,i) -> (i -> a) -> Table i a 
mkTable (i,j) f = T [f i | i <- range (i,j)] i

-- Cuando accedemos un valor en la tabla le restamos el shift al indice
lookupT :: Table Int a -> Int -> a
lookupT (T t s) i = t !! (i-s)


-- Tabla implementada con arrays funcionales (no mutables)

type TableArr i a = Array i a

mkTable_Arr :: Ix i => (i,i) -> (i -> a) -> TableArr i a 
mkTable_Arr bnds f = array bnds [(i, f i) | i <- range bnds]

lookupT_Arr  :: Ix i => TableArr i a -> i -> a
lookupT_Arr t i = t ! i


-- Fibonacci

fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n-1) + fib (n-2)

-- Fibonacci con tabulacion (tabla con listas)

fibT :: Int -> Int 
fibT n = lookupT fibTab n
  where
    -- generacion de la tabla
    fibTab = mkTable (0,n) f
    f 0 = 0
    f 1 = 1
    f n = lookupT fibTab (n-2) + lookupT fibTab (n-1)

-- Fibonacci con tabulacion (tabla con arrays)

fibT_Arr :: Int -> Int 
fibT_Arr n = lookupT_Arr fibTab n
  where
    -- generacion de la tabla
    fibTab = mkTable_Arr (0,n) f
    f 0 = 0
    f 1 = 1
    f n = lookupT_Arr fibTab (n-2) + lookupT_Arr fibTab (n-1)


-- Tabla bidimensional con listas

data Table2 i a = T2 [[a]] i i 
  deriving Show

mkTable2 ((i,j),(k,l)) f = T2 ([[(f (x,y)) | y <- range (j,l)]
                                           | x <- range (i,k)]) i j

lookupT2 (T2 t si sj) (i,j) = (t !! (i-si)) !! (j-sj)


-- Combinaciones

comb :: Int -> Int -> Int
comb m 0 = 1
comb m n
     | m == n = 1
     | m >  n = comb (m-1) (n-1) + comb (m-1) n

-- Combinaciones con tabulacion (tabla con listas)

combT :: Int -> Int -> Int 
combT m n = lookupT2 combTab (m,n)
    where
      -- generacion de la tabla
      combTab = mkTable2 ((0,0),(m,n)) f
      f (i,0) = 1
      f (i,j) | i == j = 1
              | i > j  = lookupT2 combTab (i-1,j-1)  
                       + lookupT2 combTab (i-1,j)

-- Combinaciones con tabulacion (tabla con arrays)

combT_Arr :: Int -> Int -> Int 
combT_Arr m n = lookupT_Arr combTab (m,n)
    where
      -- generacion de la tabla
      combTab = mkTable_Arr ((0,0),(m,n)) f
      f (i,0) = 1
      f (i,j) | i == j = 1
              | i > j  = lookupT_Arr combTab (i-1,j-1)  
                       + lookupT_Arr combTab (i-1,j)


-- Largo de la mayor subsecuencia comun: se analizan
-- subsecuencias formadas por elementos no necesariamente
-- contiguos

commonsub xs ys = csub 0 0 
  where
    m = length xs 
    n = length ys
    csub j k
      | j == m || k == n   = 0
      | xs !! j == ys !! k = 1 + csub (j+1) (k+1)
      | otherwise          = max (csub j (k+1)) (csub (j+1) k)


-- Version con tabulacion (tabla con listas)

commonsubT xs ys = lookupT2 csubT (0,0) 
  where
    m = length xs 
    n = length ys
    -- generacion de la tabla
    csubT = mkTable2 ((0,0),(m,n)) f
    f (j,k)
      | j == m || k == n   = 0
      | xs !! j == ys !! k = 1 + lookupT2 csubT (j+1,k+1)
      | otherwise          = max (lookupT2 csubT (j,k+1))
                                 (lookupT2 csubT (j+1,k))        

-- Version con tabulacion (tabla con listas)

commonsubT_Arr xs ys = lookupT_Arr csubT (0,0) 
  where
    m = length xs 
    n = length ys
    -- generacion de la tabla
    csubT = mkTable_Arr ((0,0),(m,n)) f
    f (j,k)
      | j == m || k == n   = 0
      | xs !! j == ys !! k = 1 + lookupT_Arr csubT (j+1,k+1)
      | otherwise          = max (lookupT_Arr csubT (j,k+1))
                                 (lookupT_Arr csubT (j+1,k))        

-- Edit distance: minimo de operaciones de edicion necesarias
-- para igualar dos secuencias. Las operaciones son: insercion,
-- borrado y reemplazo de un elemento. Esta medida es conocida
-- como "Distancia de Levenshtein" 

lev :: Eq a => [a] -> [a] -> Integer
lev [] []     = 0
lev [] (c:cs) = 1 + lev [] cs
lev (c:cs) [] = 1 + lev cs []
lev s@(c:cs) s'@(c':cs') 
  | c == c'   = lev cs cs'
  | otherwise = 1 + min3 (lev cs cs') -- reemplazo 
                         (lev cs s')  -- borrado
                         (lev s cs')  -- insercion

min3 x y z = x `min` y `min` z

-- Esta version es igual a la anterior pero implementa la recursion
-- de la misma forma que lo vamos a hacer en la version con tabulacion.
-- Se traduce la recursion sobre las secuencias de entadada en una
-- recursion sobre la posicion en las listas. 

lev1 :: Eq a => [a] -> [a] -> Integer
lev1 xs ys = levR 0 0
  where
    m = length xs
    n = length ys
    levR i j | i == m && j == n = 0 
    levR i j | i == m = 1 + levR i (j+1)
    levR i j | j == n = 1 + levR (i+1) j
    levR i j
      | xs !! i == ys !! j = levR (i+1) (j+1)
      | otherwise          = 1 + min3 (levR (i+1) (j+1)) -- reemplazo 
                                      (levR (i+1) j)     -- borrado
                                      (levR i (j+1))     -- insercion

-- Version modificada con tabulacion (tabla con listas)

lev1T xs ys = lookupT2 levTab (0,0)
  where
    m = length xs
    n = length ys
    -- generacion de la tabla
    levTab = mkTable2 ((0,0),(m,n)) f
    f (i,j) | i == m && j == n = 0
    f (i,j) | i == m = 1 + lookupT2 levTab (i,j+1)
    f (i,j) | j == n = 1 + lookupT2 levTab (i+1,j)
    f (i,j)
      | xs !! i == ys !! j = lookupT2 levTab (i+1,j+1)
      | otherwise          = 1 + min3 (lookupT2 levTab (i+1,j+1))
                                      (lookupT2 levTab (i+1,j))
                                      (lookupT2 levTab (i,j+1))  

-- Version modificada con tabulacion (tabla con arrays)

lev1T_Arr xs ys = lookupT2 levTab (0,0)
  where
    m = length xs
    n = length ys
    -- generacion de la tabla
    levTab = mkTable2 ((0,0),(m,n)) f
    f (i,j) | i == m && j == n = 0
    f (i,j) | i == m = 1 + lookupT2 levTab (i,j+1)
    f (i,j) | j == n = 1 + lookupT2 levTab (i+1,j)
    f (i,j)
      | xs !! i == ys !! j = lookupT2 levTab (i+1,j+1)
      | otherwise          = 1 + min3 (lookupT2 levTab (i+1,j+1))
                                      (lookupT2 levTab (i+1,j))
                                      (lookupT2 levTab (i,j+1))  

-- Version hibrida que maneja los indices y los substrings

lev2 :: Eq a => [a] -> [a] -> Integer
lev2 xs ys = levR 0 0 xs ys
  where
    m = length xs
    n = length ys
    levR i j [] []     = 0 
    levR i j [] (c:cs) = 1 + levR i (j+1) [] cs 
    levR i j (c:cs) [] = 1 + levR (i+1) j cs []
    levR i j s@(c:cs) s'@(c':cs')
      | c == c'   = levR (i+1) (j+1) cs cs'
      | otherwise = 1 + min3 (levR (i+1) (j+1) cs cs')  
                             (levR (i+1) j cs s')     
                             (levR i (j+1) s cs')   

-- Version hibrida con tabulacion
-- Esta version no es util. El hecho de generar funciones en
-- cada lugar de la tabla hace que se pierda el sharing de
-- valores. La razon es que cada vez que se llama a una celda
-- se recomputa la funcion que esta almacenada en la misma.
-- Notar que en los casos anteriores lo que se almcenaba en
-- cada celda es la "formula" para computar el valor que va
-- en esa celda. Dicho valor se computa a lo mas una vez. 

lev2T xs ys = lookupT2 levTab (0,0) xs ys 
  where
    m = length xs
    n = length ys
    -- generacion de la tabla
    levTab = mkTable2 ((0,0),(m,n)) f
    f (i,j) [] []     = 0
    f (i,j) [] (c:cs) = 1 + lookupT2 levTab (i,j+1) [] cs 
    f (i,j) (c:cs) [] = 1 + lookupT2 levTab (i+1,j) cs [] 
    f (i,j) s@(c:cs) s'@(c':cs')
      | c == c'   = lookupT2 levTab (i+1,j+1) cs cs'
      | otherwise = 1 + min3 (lookupT2 levTab (i+1,j+1) cs cs')
                             (lookupT2 levTab (i+1,j) cs s')
                             (lookupT2 levTab (i,j+1) s cs')  

