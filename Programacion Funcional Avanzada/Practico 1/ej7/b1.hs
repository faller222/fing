main = print $ f [1 .. 5000] [1 .. 5000 ]

f = \xs ys ->  foldr (:) ys xs
