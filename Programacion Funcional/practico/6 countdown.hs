{-
   Basado en el art?culo
   The countdown problem de Graham Hutton
   Journal of Functional Programming 12(6), 2002
-}


data Op = Add | Sub | Mul | Div
--  deriving Show

instance Show Op where
  show Add = " + "
  show Sub = " - "
  show Mul = " * "
  show Div = " / "


valid :: Op -> Int -> Int -> Bool
valid Add  _  _  = True
valid Sub  x  y  = x > y
valid Mul  _  _  = True
valid Div  x  y  = x `mod` y == 0

apply :: Op -> Int -> Int -> Int
apply Add  x  y  = x + y
apply Sub  x  y  = x - y
apply Mul  x  y  = x * y
apply Div  x  y  = x `div` y

data Expr = Val Int | App Op Expr Expr
--  deriving Show

instance Show Expr where
  show (Val n) = show n
  show (App o e e') = "(" ++ show e ++ 
                             show o ++ show e' ++ ")"

values :: Expr -> [Int]
values (Val n)      = [n]
values (App _ l r)  = values l ++ values r

eval :: Expr -> [Int]
eval (Val n)       = [n | n > 0]
eval (App op l r)  = [apply op x y  |  x <- eval l
                                    ,  y <- eval r
                                    ,  valid op x y]

subs  :: [a] -> [[a]]
subs []      = [[]]
subs (x:xs)  =  yss ++ map (x:) yss
       where yss = subs xs

interleave :: a -> [a] -> [[a]]
interleave x []      = [[x]]
interleave x (y:ys)  = (x:y:ys) : map (y:) (interleave x ys)

perms :: [a] -> [[a]]
perms []      = [[]]
perms (x:xs)  = [zs | ys <- perms xs, zs <- interleave x ys]

choices :: [a] -> [[a]]
choices xs  = [zs | ys <- subs xs, zs <- perms ys]

solution :: Expr -> [Int] -> Int -> Bool
solution e ns n = elem (values e) (choices ns) && eval e == [n]

split :: [a] -> [([a],[a])]
split []      = []
split [_]     = []
split (x:xs)  = ([x],xs) : [(x:ls,rs) | (ls,rs) <- split xs]

{- brute force solution -}

exprs :: [Int] -> [Expr]
exprs []   = []
exprs [n]  = [Val n]
exprs ns   = [e  |  (ls,rs) <- split ns
                 ,  l <- exprs ls
                 ,  r <- exprs rs
                 ,  e <- combine l r]

combine :: Expr -> Expr -> [Expr]
combine l r = [App op l r | op <- [Add,Sub,Mul,Div]]

solutions :: [Int] -> Int -> [Expr]
solutions ns n = [e  |  ns' <- choices ns
                     ,  e <- exprs ns'
                     ,  eval e == [n]]

{- combining generartion and evaluation -}

type Result = (Expr,Int)

results :: [Int] -> [Result]
results []   = []
results [n]  = [(Val n,n) | n > 0]
results ns   = [res  |  (ls,rs) <- split ns
                     ,  lx <- results ls
                     ,  ly <- results rs
                     ,  res <- combine' lx ly]

combine' :: Result -> Result -> [Result]
combine' (l,x) (r,y)
       = [(App op l r, apply op x y)  |  op <- [Add,Sub,Mul,Div]
                                      ,  valid op x y]

solutions' :: [Int] -> Int -> [Expr]
solutions' ns n = [e  |  ns' <- choices ns
                      ,  (e,m) <- results ns'
                      ,  m == n]

{- explotando propiedades algebraicas -} 

solutions2 :: [Int] -> Int -> [Expr]
solutions2 ns n = [e  |  ns' <- choices ns
                      ,  (e,m) <- results2 ns'
                      ,  m == n]

results2 :: [Int] -> [Result]
results2 []   = []
results2 [n]  = [(Val n,n) | n > 0]
results2 ns   = [res  |  (ls,rs) <- split ns
                      ,  lx <- results2 ls
                      ,  ly <- results2 rs
                      ,  res <- combine2 lx ly]

combine2 :: Result -> Result -> [Result]
combine2 (l,x) (r,y)
       = [(App op l r, apply op x y)  |  op <- [Add,Sub,Mul,Div]
                                      ,  valid' op x y]

valid' :: Op -> Int -> Int -> Bool
valid' Add  x  y  = x <= y
valid' Sub  x  y  = x > y
valid' Mul  x  y  = x /= 1 && y/= 1 && x <= y
valid' Div  x  y  = y /= 1 && x `mod` y == 0

{- Ejemplo -}

ns = [1,3,7,10,25,50]
n = 765

-- ns = [1,10,25,50,75,100]
-- n = 813

pSol = sequence_ . map putStrLn . map show

ej  = pSol $ solutions ns n
ej' = pSol $ solutions' ns n
ej2 = pSol $ solutions2 ns n
