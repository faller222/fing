module A (foo, bar) where

data T = CT Int

foo :: T -> T -> Int
foo (CT x ) (CT y) = x + y

bar :: Int -> T
bar x = CT x
