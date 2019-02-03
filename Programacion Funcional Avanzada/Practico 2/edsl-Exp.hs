{-# Language FlexibleInstances, RecordWildCards #-}

module DSL_Exp where

-- | AST para Excel

data Exp = LitDbl Double
         | LitStr String
         | LitBool Bool
         | Apply Func [Exp]
         | Var Id
     deriving (Eq, Show)

type Func = String
type Id = String

-- | Phantom type for expressions

data E a = E Exp

instance Show a => Show (E a) where
  show (E x) = show x

instance Eq a => Eq (E a) where
  E x == E y = x == y

instance Num (E Double) where
  E x + E y     = E (Apply "+" [x, y])
  E x - E y     = E (Apply "-" [x, y])
  E x * E y     = E (Apply "*" [x, y])
  abs    (E x)  = E (Apply "abs" [x])
  signum (E x)  = E (Apply "signum" [x])
  fromInteger x = E (LitDbl (fromInteger x))

evalD :: E Double -> Double
evalD (E(LitDbl d)) = d

-- | component

data Adder = Adder {
  x :: E Double,
  y :: E Double,
  z :: E Double }

instance Show Adder where
  show Adder {..} =  grid [("x", show x),
                           ("y", show y),
                           ("x+y", show z)]
       where
         grid [] = ""
         grid [(x,s)] = x ++ "=" ++ s
         grid ((x,s) : ps) = x ++ "=" ++ s ++ " | " ++ grid ps

