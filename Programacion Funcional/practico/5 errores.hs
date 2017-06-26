data Expr  =  Lit   Int 
           |  Add   Expr Expr  |  Sub  Expr Expr 
           |  Mul   Expr Expr  |  Div  Expr Expr   

eval :: Expr -> Int
eval (Lit  v)      = v 
eval (Add  e1 e2)  = eval e1 + eval e2 
eval (Sub  e1 e2)  = eval e1 - eval e2
eval (Mul  e1 e2)  = eval e1 * eval e2 
eval (Div  e1 e2)  = eval e1 `div` eval e2


myDiv1 :: Int -> Int -> Int
myDiv1 x y | y /= 0    = div x  y

myDiv2 :: Int -> Int -> Int
myDiv2 x y | y /= 0    = div x  y
           | otherwise = error "Division por cero"

myDiv3 :: Int -> Int -> Int
myDiv3 x y | y /= 0    = div x  y
           | otherwise = 0

myDiv4 :: Int -> Int -> Int -> Int
myDiv4 x y z | y /= 0    = div x  y
             | otherwise = z

myDiv5 :: Int -> Int -> Maybe Int
myDiv5 x y | y /= 0    = Just (div x  y)
           | otherwise = Nothing

myDiv6 :: Int -> Int -> Either String Int
myDiv6 x y | y /= 0    = Right (div x  y)
           | otherwise = Left  "Division por Cero"


evalMaybe :: Expr -> Maybe Int
evalMaybe (Lit  v)      = Just v
evalMaybe (Add  e1 e2)  = evalOp (+) e1 e2
evalMaybe (Sub  e1 e2)  = evalOp (-) e1 e2
evalMaybe (Mul  e1 e2)  = evalOp (*) e2 e2 
evalMaybe (Div  e1 e2)  = evalDiv e1 e2

evalOp op e1 e2 = case (evalMaybe e1, evalMaybe e2) of
                           (Just v1, Just v2) -> Just (op v1 v2)
                           _                  -> Nothing 
evalDiv e1 e2 =  case (evalMaybe e1, evalMaybe e2) of
                           (Just v1, Just v2) -> if v2 == 0 then Nothing
                                                            else Just (div v1 v2)
                           _                  -> Nothing
