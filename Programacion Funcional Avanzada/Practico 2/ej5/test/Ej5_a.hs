-- module Ej5_a where

type Html = String

text :: String -> Html
text = id

bold :: Html -> Html
bold = wrap "b"

italics :: Html -> Html
italics = wrap "i"

underline :: Html -> Html
underline = wrap "u"

url :: String -> Html -> Html
url t = (("<a href=\"" ++ t ++ "\">")++).(++"</a>")

size :: Int -> Html -> Html
size i = (("<span style=\"font-size:" ++ show i ++ "px\">")++).(++"</span>")

list :: [Html] -> Html
list = (wrap "ul").concatMap(wrap "li")

(<->) :: Html -> Html -> Html
h <-> h' = h ++ "<br/>" ++ h'

(<+>) :: Html -> Html -> Html
h <+> h' = h ++ h'

generate :: Html -> String
generate  = id

wrap ::  String -> Html -> Html
wrap tag = (("<" ++ tag ++ ">")++).(++("</"++tag++">"))

-- ("<>"++).(++"<.>")

ex1 = text "hola soy un texto sin formato"

ex2 = (bold . text) "hola soy bold" <+> (underline . bold . text) " y subrayado"

ex3 = ex1 <-> size 35 (ex2 <+> text " y grande") <-> text "consultar en: " <+> url "http://www.google.com" ((bold . text) "Google")

ex4 = text "la lista es:" <+> list [ex1 , ex2 , (italics . text) "y nada mas"]

main = do writeFile "ex1.html" (generate ex1 )
          writeFile "ex2.html" (generate ex2 )
          writeFile "ex3.html" (generate ex3 )
          writeFile "ex4.html" (generate ex4 )
