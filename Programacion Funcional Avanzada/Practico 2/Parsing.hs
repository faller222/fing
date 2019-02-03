module Parsing where

import Prelude hiding ((<*>),(<$>),(<*),(*>),(<$))
import Data.Char

type Parser s a = [s] -> [(a, [s])]

pFail  :: Parser s a
pFail  = \cs -> []

pSucceed    :: a -> Parser s a
pSucceed a  =  \cs -> [(a,cs)]

pSym    :: Eq s => s -> Parser s s
pSym s  = \cs ->  case cs of
                    []         ->  []
                    (c : cs')  ->  if  c == s
                                       then [(c,cs')]
                                       else []

(<|>) ::  Parser s a -> Parser s a -> Parser s a
p <|> q = \cs -> p cs ++ q cs

(<*>) ::  Parser s (a -> b) -> Parser s a -> Parser s b
(p <*> q) cs = [ (f a, cs'')  |  (f , cs')   <- p cs
                              ,  (a , cs'')  <- q cs']

pList :: Parser s a -> Parser s [a]
pList p =  pSucceed (:) <*> p <*> pList p
           <|>
           pSucceed []

f <$> p  = pSucceed f <*> p

p <* q   = (\ x _ -> x) <$> p <*> q

p *> q   = (\ _ y -> y) <$> p <*> q

a <$ q   = pSucceed a <* q

p `opt` v = p <|> pSucceed v

pSat    :: (s -> Bool) -> Parser s s
pSat p  = \cs ->  case cs of
                   []         -> []
                   (c : cs')  -> if  p c
                                     then [(c,cs')]
                                     else []

-- | Ejemplos

pA2B = pSucceed (\_ -> 'B') <*> pSym 'A'

pAB = pSucceed (,) <*> pSym 'A' <*> pSym 'B'

pListAB = pList pAB

pParens1 p = pSym '(' *> p <* pSym ')'

pParens2 p = id <$ pSym '(' <*> p <* pSym ')'

-- isDigit c = (c >= '0') && (c <= '9')

pDigit = pSat isDigit

pDigits = pList pDigit

digit = d2int <$> pDigit
  where d2int d = ord(d) - ord('0')
        
digits = pList digit
-- otra forma
-- digits = map d2int <$> pDigits

number = foldl (\n d -> n*10 + d) 0 <$> digits


