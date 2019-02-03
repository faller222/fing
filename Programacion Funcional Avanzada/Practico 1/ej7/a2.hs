main = print $ f2 [1 .. 5000]

f2 = foldr (\x r -> r ++ [x ]) []
