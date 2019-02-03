-- module Ej5_d where

data Html = Text String
   | Bold Html
   | Italics Html
   | Underline Html
   | Url String Html
   | Size Int Html
   | List [Html]
   | NewLine Html Html
   | Concat Html Html

text = Text
bold = Bold
italics = Italics
underline = Underline
url = Url
size = Size
list = List
(<->) = NewLine
(<+>) = Concat

generate :: Html -> Int
generate (Text _) = 1
generate (Bold h) = generate h
generate (Italics h) = generate h
generate (Underline h) = generate h
generate (Url _ h) = generate h
generate (Size _ h) = generate h
generate (List hs) = (1+).sum $ map generate hs
generate (NewLine h h') = generate h + generate h'
generate (Concat h h') = generate h + generate h' - 1


ex1 = text "hola soy un texto sin formato"

ex2 = (bold . text) "hola soy bold" <+> (underline . bold . text) " y subrayado"

ex3 = ex1 <-> size 35 (ex2 <+> text " y grande") <-> text "consultar en: " <+> url "http://www.google.com" ((bold . text) "Google")

ex4 = text "la lista es:" <+> list [ex1 , ex2 , (italics . text) "y nada mas"]

main = do writeFile "ex1.html" (show $ generate ex1 )
          writeFile "ex2.html" (show $ generate ex2 )
          writeFile "ex3.html" (show $ generate ex3 )
          writeFile "ex4.html" (show $ generate ex4 )
