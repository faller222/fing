main = print $ f2 [1 .. 5000 ] [1 .. 5000 ]

f2 = foldl (\k x ! k : (x :)) id
