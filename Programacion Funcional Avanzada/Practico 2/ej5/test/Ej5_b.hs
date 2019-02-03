-- module Ej5_b where

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

generate :: Html -> String
generate (Text t) = t
generate (Bold h) = wrap "b" (generate h)
generate (Italics h) = wrap "i" (generate h)
generate (Underline h) = wrap "u" (generate h)
generate (Url t h) = ((("<a href=\"" ++ t ++ "\">")++).(++"</a>")) (generate h)
generate (Size i h) = ((("<span style=\"font-size:" ++ show i ++ "px\">")++).(++"</span>")) (generate h)
generate (List hs) = wrap "ul" (concatMap ((wrap "li").(generate)) hs)
generate (NewLine h h') = generate h ++ "<br/>" ++ generate h'
generate (Concat h h') = generate h ++ generate h'


wrap ::  String -> String -> String
wrap tag = (("<" ++ tag ++ ">")++).(++("</"++tag++">"))

ex1 = text "hola soy un texto sin formato"

ex2 = (bold . text) "hola soy bold" <+> (underline . bold . text) " y subrayado"

ex3 = ex1 <-> size 35 (ex2 <+> text " y grande") <-> text "consultar en: " <+> url "http://www.google.com" ((bold . text) "Google")

ex4 = text "la lista es:" <+> list [ex1 , ex2 , (italics . text) "y nada mas"]

main = do writeFile "ex1.html" (generate ex1 )
          writeFile "ex2.html" (generate ex2 )
          writeFile "ex3.html" (generate ex3 )
          writeFile "ex4.html" (generate ex4 )
