import Prelude hiding (Word)


type Doc   = String
type Line  = String
type Word  = String


ejemplo = "Aqui me pongo a cantar\nAl compas de la viguela,\nque el hombre que lo desvela\nuna pena estrordinaria,\ncomo la ave solitaria\ncon el cantar se consuela."

-- funcion que genera el indice de palabras
mkIndex :: Doc -> [([Int],Word)]
mkIndex = shorten 3 . amalgamate . makeLists . sortLs . allNumWords . numLines . lines

-- parte el string en una lista de lineas
-- lines :: Doc -> [Line]

-- combina las lineas con su numero respectivo
numLines :: [Line] -> [(Int, Line)]
numLines ls = zip [1 .. length ls] ls 

-- parte las lineas en palabras con su numero de linea
allNumWords :: [(Int, Line)] -> [(Int, Word)]
allNumWords = concat . map numWords

numWords (n,l) = map (\w -> (n,w)) (splitWords l)

splitWords = filter (/= "") . lines . map white2line
  where whitespace = " \n\t;:.,\'\"!?()-"
        white2line c | elem c whitespace = '\n'
                     | otherwise         = c 

-- ordena (quicksort) pares de numero de linea y palabra
sortLs :: [(Int, Word)] -> [(Int, Word)]  
sortLs []     = []
sortLs (p:ps) = sortLs smaller ++ [p] ++ sortLs larger
     where smaller = [q | q <- ps, orderPair q p]
           larger  = [q | q <- ps, orderPair p q]

-- condicion de orden: por palabra y luego por numero
orderPair (n1,w1) (n2,w2) = w1<w2 || (w1==w2 && n1<n2) 


-- pasa los numeros a listas
makeLists :: [(Int, Word)] -> [([Int], Word)]
makeLists = map (\(n,st) -> ([n],st))


-- junta instancias de la misma palabra
amalgamate :: [([Int], Word)] -> [([Int], Word)]
amalgamate []  = []
amalgamate [p] = [p]
amalgamate ((l1,w1):(l2,w2):rest)
   | w1 /= w2  = (l1,w1) : amalgamate ((l2,w2):rest)
   | otherwise = amalgamate ((l1++l2,w1):rest) 

-- quita palabras de largo menor que n
shorten :: Int -> [([Int], Word)] -> [([Int], Word)]
shorten n = filter (\(_,w) -> length w > n)

