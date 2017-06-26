import Prelude hiding ((<*>))




-- tipo de las expresiones regulares
type RegExp = String -> Bool

-- reconoce el string vacio
epsilon :: RegExp
epsilon = (== "")

test1 = epsilon ""
test2 = epsilon "aaaa"

-- reconoce un caracter
char :: Char -> RegExp
char ch = (==[ch])

re_a = char 'a'
re_b = char 'b'

test3 = re_a "a"
test4 = re_a "aaaa"
test5 = re_a "" 
test6 = re_b "a"


infixr 5 <|>

-- or de dos expresiones regulares
(<|>) :: RegExp -> RegExp -> RegExp
e1 <|> e2 = \s -> e1 s || e2 s


re_aob = re_a <|> re_b
re_aoe = re_a <|> epsilon

test7  = re_aob "a"
test8  = re_aob "b"
test9  = re_aob "ab"
test10 = re_aoe "a"
test11 = re_aoe ""

infixr 7 <*>

-- secuencia de expresiones regulares
(<*>) :: RegExp -> RegExp -> RegExp
e1 <*> e2 = \s -> or [ e1 s1 && e2 s2 | (s1,s2) <- splits s]


splits :: String -> [(String,String)]
splits s = [splitAt i s | i <- [0 .. length s]]

splHola = splits "Hola"

re_ab = re_a <*> re_b
re_ea = epsilon <*> re_a

test12 = re_ab "ab" 
test13 = re_ab "aab" 
test14 = re_ab ""
test15 = re_ea "a"


-- ?
question :: RegExp -> RegExp
question e =  epsilon <|> e


-- star

 
star :: RegExp -> RegExp
-- falla si e reconoce epsilon
--star e = epsilon <|> (e <*> star e) 
star e = epsilon <|> (e <**> star e) 
  where e1 <**> e2 = \s -> or [ e1 s1 && e2 s2 | (s1,s2) <- splits s, s1 /= "" ]


re_as  = star re_a
re_abs = star re_ab

test16 = re_as "aaaaaa"
test17 = re_as ""
test18 = re_abs "abababab"
test19 = re_abs "aaab"
test20 = star (question re_a) ""
test21 = star (question re_a) "aa"


-- plus
plus :: RegExp -> RegExp
plus e = e <*> star e

-- exact
exact :: RegExp -> Int -> RegExp
exact _ 0 = epsilon
exact e n = e <*> exact e (n-1) 

