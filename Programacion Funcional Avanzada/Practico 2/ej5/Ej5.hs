{-# Language FlexibleInstances, RecordWildCards #-}

module Ej5 where


-- | Html DSL

class DSL_Html h where
  text :: String -> h
  bold :: h -> h
  italics :: h -> h
  underline :: h -> h
  url :: String -> h -> h
  size :: Int -> h -> h
  list :: [h] -> h
  (<->) :: h -> h -> h
  (<+>) :: h -> h -> h
  generate :: h -> String

wrap ::  String -> String -> String
wrap tag = (("<" ++ tag ++ ">")++).(++("</"++tag++">"))

-- | Shallow embedding

type SHtml = String

instance DSL_Html SHtml where
  text             = id
  bold             = wrap "b"
  italics          = wrap "i"
  underline        = wrap "u"
  url t            = (("<a href=\"" ++ t ++ "\">")++).(++"</a>")
  size i           = (("<span style=\"font-size:" ++ show i ++ "px\">")++).(++"</span>")
  list             = (wrap "ul").concatMap(wrap "li")
  h <-> h'         = h ++ "<br/>" ++ h'
  h <+> h'         = h ++ h'
  generate         = id

-- | Deep embedding

data DHtml = Text String
           | Bold DHtml
           | Italics DHtml
           | Underline DHtml
           | Url String DHtml
           | Size Int DHtml
           | List [DHtml]
           | NewLine DHtml DHtml
           | Concat DHtml DHtml
    deriving (Eq, Show)

instance DSL_Html DHtml where
    text t = Text t
    bold h = Bold h
    italics h = Italics h
    underline h = Underline h
    url u h = Url u h
    size i h = Size i h
    list hs = List hs
    h <-> h' = NewLine h h'
    h <+> h' = Concat h h'

    generate (Text t) = t
    generate (Bold h) = wrap "b" (generate h)
    generate (Italics h) = wrap "i" (generate h)
    generate (Underline h) = wrap "u" (generate h)
    generate (Url t h) = ((("<a href=\"" ++ t ++ "\">")++).(++"</a>")) (generate h)
    generate (Size i h) = ((("<span style=\"font-size:" ++ show i ++ "px\">")++).(++"</span>")) (generate h)
    generate (List hs) = wrap "ul" (concatMap ((wrap "li").(generate)) hs)
    generate (NewLine h h') = generate h ++ "<br/>" ++ generate h'
    generate (Concat h h') = generate h ++ generate h'
