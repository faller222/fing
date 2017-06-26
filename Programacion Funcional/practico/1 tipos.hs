-- tipo Empleado

juan :: (String, Int, Int)
juan  = ("Juan", 23, 60000)

pedro :: (String, Int, Int)
pedro = ("Pedro", 44, 60000)

nombre (n,_,_) = n
edad   (_,e,_) = e
sueldo (_,_,s) = s

esPedro ("Pedro",  _,  _) = True
esPedro (_,        _,  _) = False

-- type Empleado = (String, Int, Int)
type Empleado = (Nombre, Edad, Salario)
type Nombre   = String
type Edad     = Int
type Salario  = Int

jose :: Empleado
jose = ("Jose", 36, 65000)

mayor :: Empleado -> Empleado -> Nombre
mayor (n1,e1,_) (n2,e2,_) 
        | e1 > e2   = n1
        | otherwise = n2 

-- funciones sobre pares

pair :: (a -> b, a -> c) -> a -> (b,c)
pair (f,g) x = (f x, g x)

type Posicion  = (Float, Float)
type Angulo    = Float
type Distancia = Float

mover :: Distancia -> Angulo -> Posicion -> Posicion
mover d a (x,y) = (x + d * cos a, y + d * sin a)

-- lista de empleados

type Empleados = [Empleado]

empleados :: Empleados 
empleados = [juan,pedro,jose]

isEven n = n `mod` 2 == 0

l1 = [x | x <- [1,2,3,4]]
l2 = [x | x <- [1,2,3,4], isEven x]
l3 = [isEven x | x <- [1,2,3,4]]
l4 = [(x+y) | (x,y) <- [(1,2),(2,3),(3,4)]]
l5 = [(x,y) | x <- [1,2], y <- [3,4]]

evens xs =  [x | x <- xs, isEven x]

satisfyP :: [Int] -> (Int -> Bool) -> [Int]
satisfyP xs p =  [x | x <- xs, p x]

data PuntoCardinal = Norte | Sur | Este | Oeste
      deriving (Show,Eq)

data Empleado' = Empleado String Int Int
      deriving (Show,Eq)

data Sueldo  =  Asalariado  Int
             |  Jornalero   [Int] 
      deriving (Show,Eq)

liquidar :: Sueldo -> Int
liquidar (Asalariado  m)  = m
liquidar (Jornalero   js) = sum js
