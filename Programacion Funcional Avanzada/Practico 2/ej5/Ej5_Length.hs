{-# Language FlexibleInstances, RecordWildCards #-}

module Ej5_Length where


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

-- | Shallow embedding

type SHtml = Integer

instance DSL_Html SHtml where
  text             = const 1
  bold             = id
  italics          = id
  underline        = id
  url t            = id
  size i           = id
  list             = (1+).sum
  (<->)            = (+)
  (<+>)            = curry ((-1+).(uncurry (+)))
  generate         = show

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

    generate h = show $ generate' h


generate' :: DHtml -> Int
generate' (Text _) = 1
generate' (Bold h) = generate' h
generate' (Italics h) = generate' h
generate' (Underline h) = generate' h
generate' (Url _ h) = generate' h
generate' (Size _ h) = generate' h
generate' (List hs) = (1+).sum $ map generate' hs
generate' (NewLine h h') = generate' h + generate' h'
generate' (Concat h h') = generate' h + generate' h' - 1