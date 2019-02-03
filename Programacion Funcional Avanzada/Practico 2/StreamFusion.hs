{-# LANGUAGE ExistentialQuantification, GADTs #-} 

module StreamFusion where
       
import Prelude hiding (repeat,
                       replicate,
                       enumFromTo,
                       map,
                       filter,
                       foldr,
                       foldl,
                       (++))

-- | Coalgebras de listas: funciones de tipo (s -> Maybe (a,s))
-- | Las colagebras son como automatas con estados de tipo s

-- | Ejecutor de coalgebras, generador de listas
-- | Se ejecuta el automata a partir de un estado inicial

unfoldr :: (s -> Maybe (a,s)) -> s -> [a]
unfoldr next s = case next s of
                   Nothing     -> []
                   Just (a,s') -> a : unfoldr next s'

-- | Ejemplo de generadores de listas

-- genera lista infinita de x's 
repeatC :: a -> [a]
repeatC = unfoldr (\x -> Just (x,x))

-- genera una lista x's de largo n
replicateC :: (Num a, Eq a) => a -> b -> [b]
replicateC n x  = unfoldr (\n -> if n == 0 then Nothing
                                           else Just (x,n-1)) n
-- genera el intervalo entre b y e
enumFromToC    :: (Ord a, Num a) => a -> a -> [a]
enumFromToC b e = unfoldr (\b -> if b > e then Nothing
                                          else Just (b,b+1)) b
-- map como generador
mapC  :: (a -> b) -> [a] -> [b]
mapC f = unfoldr gMap
   where gMap []     = Nothing
         gMap (x:xs) = Just (f x,xs)

-- filter como generador
filterC  :: (a -> Bool) -> [a] -> [a]
filterC p = unfoldr gFil
   where gFil []     = Nothing
         gFil (x:xs) = if p x then Just (x,xs)
                              else gFil xs     -- gFil es recursiva

-- | Representacion de listas como "coalgebras" 

data Repr a = forall s . Repr (s -> Maybe (a,s)) s

-- | Los tipos de Repr y unfoldr son muy similares
-- | 
-- |   Repr    :: (s -> Maybe (a,s)) -> s -> Repr a
-- |   unfoldr :: (s -> Maybe (a,s)) -> s -> [a]
 
-- | Conversion de lista a Repr

toRepr :: [a] -> Repr a
toRepr xs = Repr next xs
   where next []     = Nothing
         next (x:xs) = Just (x,xs)
  
-- |  Conversion de Repr a lista
-- | (la coalgera contenida en Repr es "ejecutada")

fromRepr :: Repr a -> [a]
fromRepr (Repr next s) = unfoldr next s

-- | Conversion de funcions sobre listas en funcions sobre Repr.
-- | La idea es que las funciones sobre Repr sean no recursivas.

repeatR :: a -> [a]
repeatR = fromRepr . repeatRepr

repeatRepr :: a -> Repr a
repeatRepr a = Repr (\x -> Just (x,x)) a

replicateR :: (Num a, Eq a) => a -> b -> [b]
replicateR n x = fromRepr (replicateRepr n x)

replicateRepr :: (Num a, Eq a) => a -> b -> Repr b
replicateRepr n x = Repr next n
   where next n = if n == 0 then Nothing
                            else Just (x,n-1)

enumFromToR :: (Ord a, Num a) => a -> a -> [a]
enumFromToR b e = fromRepr (enumFromToRepr b e)

enumFromToRepr :: (Ord a, Num a) => a -> a -> Repr a
enumFromToRepr b e = Repr next b
   where next b = if b > e then Nothing
                           else Just (b,b+1)

mapR :: (a -> b) -> [a] -> [b]
mapR f = fromRepr . mapRepr f . toRepr

mapRepr :: (a -> b) -> Repr a -> Repr b
mapRepr f (Repr next s) = Repr next' s 
   where next' s = case next s of
                     Nothing     -> Nothing
                     Just (a,s') -> Just (f a,s')


{-
   Composiciones de funciones definidas en terminos de Repr se pueden
   fusionar aplicando la siguiente regla de reescritura, codificada como
   una RULE en GHC, junto a otras optimizaciones estandar de GHC (como 
   ser case-to-case transformation y constructor specialisation). La
   clave esta en que las funciones en terminos de Repr sean "no recursivas".
-}

-- {-# RULES
-- "toRepr/fromRepr"  forall r. toRepr (fromRepr r) = r
-- #-}

-- | Ejemplo: 

{-
  map f . map g
=
  fromRepr . mapRepr f . toRepr . fromRepr . mapRepr g . toRepr 
= 
  fromRepr . mapRepr f . mapRepr g . toRepr
=
  fromRepr . mapRepr (f . g) . toRepr
-}

-- | Problema: filter.

{-
   La coalgebra de filter es recursiva, lo que se aparta de lo que
   sucede en los otros generadores de Repr.
   Esto afecta en forma importante la aplicacion de optimizaciones.
-} 

filterR p = fromRepr . filterRepr p . toRepr
 
filterRepr :: (a -> Bool) -> Repr a -> Repr a
filterRepr p (Repr next s) = Repr next' s
   where next' s = case next s of
                     Nothing     -> Nothing
                     Just (a,s') -> if p a then Just (a,s')
                                           else next' s'

{-
   Para solucionar este problema es necesario introducir un constructor
   adicional a la representacion que modele transiciones de estado
   "silenciosas". Esto da origen a la definicion del tipo Stream usado
   en Stream Fusion.
-}

data Stream a = forall s . Stream (s -> Step a s) s
                                                
data Step a s = Done        -- corresponde a Nothing
              | Yield a s   -- corresponde a Just
              | Skip s      -- transicion silenciosa

{-
   Las conversiones hacia y desde la representacion y la "ejecucion"
   de una coalgebra son similares al caso de Repr con la inclusion
   del constructor Skip.
-}

stream :: [a] -> Stream a
stream xs = Stream next xs
   where next []     = Done
         next (x:xs) = Yield x xs

unstream :: Stream a -> [a]
unstream (Stream next s) = unfoldS next s

-- | Ejecutor de coalgebras asociadas con Stream

unfoldS :: (s -> Step a s) -> s -> [a]
unfoldS next s = unfold s
   where unfold s = case next s of
                      Done       -> []
                      Yield a s' -> a : unfold s'
                      Skip s'    -> unfold s'

-- {-# RULES
-- "stream/unstream"  forall s. stream (unstream s) = s
-- #-}

-- | Funciones en terminos de Stream

map f = unstream . mapS f . stream

mapS :: (a -> b) -> Stream a -> Stream b
mapS f (Stream next s) = Stream next' s
   where next' s = case next s of
                     Done       -> Done
                     Yield a s' -> Yield (f a) s'
                     Skip s'    -> Skip s'
                    
-- | Filter, ahora de forma no recursiva:

filter p = unstream . filterS p . stream

filterS :: (a -> Bool) -> Stream a -> Stream a
filterS p (Stream next s) = Stream next' s
   where next' s = case next s of
                     Done       -> Done
                     Skip s'    -> Skip s'
                     Yield a s' -> if p a then Yield a s'
                                          else Skip s'

-- | foldr sobre streams

-- | Es una funcion recursiva. Toda funcion que consuma un stream para
-- | retornar un valor de otro tipo debe ser recursiva ya que debe
-- | atravesar la lista de valores que genera el stream

foldr h b = foldrS h b . stream

foldrS :: (a -> b -> b) -> b -> Stream a -> b
foldrS h b (Stream next s) = go s
   where go s = case next s of
                  Done       -> b
                  Skip s'    -> go s'
                  Yield a s' -> h a (go s')

-- | foldl sobre streams

foldl f z = foldlS f z . stream

foldlS :: (b -> a -> b) -> b -> Stream a -> b
foldlS f z (Stream next s) = go z s
  where go z s = case next s of
                   Done       -> z
                   Skip s'    -> go z s'
                   Yield a s' -> go (f z a) s'
                   
-- | append sobre streams

xs ++ ys = unstream $ appendS (stream xs) (stream ys)

appendS :: Stream a -> Stream a -> Stream a
appendS (Stream nexta sa) (Stream nextb sb) = Stream next (Left sa)
  where
    next (Left sa)  = case nexta sa of
                        Done        -> Skip (Right sb)
                        Skip sa'    -> Skip (Left sa')
                        Yield x sa' -> Yield x (Left sa')
    next (Right sb) = case nextb sb of
                        Done        -> Done
                        Skip sb'    -> Skip (Right sb')
                        Yield x sb' -> Yield x (Right sb')                        
