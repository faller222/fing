module Interp (parser,eval,runP) where
-- | http://hackage.haskell.org/package/mtl-2.2.2/docs/Control-Monad-Reader.html

import Data.Char
import Control.Monad.Reader

data Expr = Let String Expr Expr
          | Add Expr Expr
          | Num Int
          | Var String
   deriving Show

-- | Parte A

type Env = [(String,Int)]
type InterM = Reader Env Int

interp :: Expr -> InterM
interp (Let s e1 e2)    = do
                          e <- ask
                          v1 <- interp e1
                          local (\_ -> concat [[(s,v1)],e]) (interp e2)
interp (Num n)          = return n
interp (Var s)          = do
                          e <- ask
                          return $ unWrap $ lookup s e
interp (Add e1 e2)      = do
                          v1 <- interp e1
                          v2 <- interp e2
                          return (v1 + v2)

unWrap :: Maybe Int -> Int
unWrap Nothing = undefined
unWrap (Just a) = a

-- | Parte B
eval :: Expr -> Int
eval exp = runReader (interp exp) []

-- | Parte C
newtype Parser a = P {runP :: String -> [(a, String)]}

instance Functor Parser where
  fmap f (P p) = P $ \s -> let xs = p s in map f' xs where
    f' (a, s) = (f a, s)

instance Applicative Parser where
  pure a  = P $ \s -> [(a,s)]
  p <*> q = P $ \s -> [(f a, s'')|(f, s') <- runP p s, (a, s'') <- runP q s']

pSym :: Char -> Parser Char
pSym c  = P $ \cs ->  case cs of
                          [] -> []
                          (c' : cs') -> if c == c'
                                        then [(c',cs')]
                                        else []

(<|>) ::  Parser a -> Parser a -> Parser a
(P p) <|> (P q) = P $ \cs -> p cs ++ q cs

pSat :: (Char -> Bool) -> Parser Char
pSat p  = P $ \cs -> case cs of
                          [] -> []
                          (c : cs') -> if  p c
                                       then [(c,cs')]
                                       else []

parser :: Parser Expr
parser = expr where
  expr, expr1, expr2, expr3, expr4 :: Parser Expr
  expr = expr1 <|> expr2 <|> expr3 <|> expr4 <|> expr5
  expr1 = (\_ _ _ _ s _ e _ e' -> Let s e e') <$> pSym 'l' <*> pSym 'e' <*> pSym 't'  <*> pSym ' ' <*> varname <*> pSym ' '  <*> expr <*> pSym ' '  <*> expr
  expr2 = (\_ _ _ _ e _ e' -> Add e e') <$> pSym 'a' <*> pSym 'd' <*> pSym 'd' <*> pSym ' '  <*> expr <*> pSym ' '  <*> expr
  expr3 = (\n -> Num $ intsToInt n) <$> nat
  expr4 = (\_ e _ -> e) <$> pSym '(' <*> expr <*> pSym ')'
  expr5 = (\s -> Var s) <$> varname

  nat, nat1, nat2 :: Parser [Int]
  nat = nat1 <|> nat2
  nat1 = (\d -> digitToInt d:[]) <$> digit
  nat2 = (\d ds -> digitToInt d:ds) <$> digit <*> nat

  varname, varname1, varname2 :: Parser String
  varname = varname1 <|> varname2
  varname1 = (\c -> c:[]) <$> lower
  varname2 = (\c cs -> c:cs) <$> lower <*> varname

  digit, lower :: Parser Char
  digit = pSat isDigit
  lower = pSat isLower

intsToInt :: [Int] -> Int
intsToInt = fst . foldr (\d (n,p) -> (d*p + n,p*10)) (0,1)
