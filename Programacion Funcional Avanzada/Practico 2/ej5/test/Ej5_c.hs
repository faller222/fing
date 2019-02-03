-- module Ej5_c where

text :: String -> Int
text = const 1

bold :: Int -> Int
bold = id

italics :: Int -> Int
italics = id

underline :: Int -> Int
underline = id

url :: String -> Int -> Int
url _ = id

size :: Int -> Int -> Int
size _ = id

list :: [Int] -> Int
list = (1+).sum

(<->) :: Int -> Int -> Int
(<->) = (+)

(<+>) :: Int -> Int -> Int
(<+>) = curry ((-1+).(uncurry (+)))

generate :: Int -> String
generate  = show


ex1 = text "hola soy un texto sin formato"

ex2 = (bold . text) "hola soy bold" <+> (underline . bold . text) " y subrayado"

ex3 = ex1 <-> size 35 (ex2 <+> text " y grande") <-> text "consultar en: " <+> url "http://www.google.com" ((bold . text) "Google")

ex4 = text "la lista es:" <+> list [ex1 , ex2 , (italics . text) "y nada mas"]

main = do writeFile "ex1.html" (generate ex1 )
          writeFile "ex2.html" (generate ex2 )
          writeFile "ex3.html" (generate ex3 )
          writeFile "ex4.html" (generate ex4 )
