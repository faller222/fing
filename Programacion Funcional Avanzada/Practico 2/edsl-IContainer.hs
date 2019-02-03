{-# LANGUAGE GADTs #-}

-- | Se representa la interfaz de un EDSL que manipula
-- | contenedores de enteros

class IContainer c where
  empty  :: c
  insert :: Int ->  c  -> c
  delete ::         c  -> c
  value  ::         c  -> Int
  list   ::         c  -> [Int]

-- | Vamos a impementar dos formas de contenedores: colas y pilas.
-- | En ambos casos escribiremos un shallow y un deep embedding. 


-- | Queue (shallow embedding)

newtype SQueue = Q [Int] -- el frente de la cola es
                         -- la cabeza de la lista

instance IContainer SQueue where
  empty             = Q []

  insert v (Q vs)   = Q (vs ++ [v])

  delete (Q (v:vs)) = Q vs
  delete q          = q

  value (Q (v:vs))  = v

  list (Q vs)       = vs

-- | Queue (deep embedding)

data DQueue where
  EmptyQ   :: DQueue
  InsertQ  :: Int -> DQueue -> DQueue
  DeleteQ  :: DQueue -> DQueue
  deriving Show

instance IContainer DQueue where
  empty  = EmptyQ

  insert = InsertQ

  delete = DeleteQ

  value  = head . list

  list   = listQ
   where
     listQ :: DQueue -> [Int]
     listQ EmptyQ = []
     listQ (InsertQ v q) = listQ q ++ [v]
     listQ (DeleteQ q)   = case listQ q of
                            []   -> []
                            v:vs -> vs

-- | Stack (shallow embedding)

newtype SStack = S [Int] -- el frente de la pila es
                         -- la cabeza de la lista

instance IContainer SStack where
  empty             = S []

  insert v (S vs)   = S (v:vs)

  delete (S (v:vs)) = S vs
  delete s          = s

  value (S (v:vs))  = v

  list (S vs)       = vs

-- | Stack (deep embedding)

data DStack where
  EmptyS :: DStack
  Push   :: Int -> DStack -> DStack
  Pop    :: DStack -> DStack
  deriving Show

instance IContainer DStack where
  empty = EmptyS

  insert = Push

  delete = Pop

  value  = head . list

  list   = listS
   where
     listS :: DStack -> [Int]
     listS EmptyS     = []
     listS (Push v s) = v : listS s
     listS (Pop s)    = case listS s of
                          []   -> []
                          v:vs -> vs



