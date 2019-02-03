main = print f

f = let xs = [1 .. 5000 ] in if length xs > 0 then head xs else 0
