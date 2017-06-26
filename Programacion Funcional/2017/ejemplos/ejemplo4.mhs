foo :: (Int,Int) -> Int
foo(x,y) = (let x :: Int = y in x)
              + (let y :: Int = x + y in y)
	     
main = foo(2,4)