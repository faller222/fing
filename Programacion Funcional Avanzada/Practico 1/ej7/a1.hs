
main = print $ f [1 .. 5000]

f = foldl (flip (:)) []
