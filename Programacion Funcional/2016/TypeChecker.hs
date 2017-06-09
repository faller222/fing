-- LABORATORIO DE PROGRAMACION FUNCIONAL 2016
-- MODULO DE CHEQUEO DE TIPOS

-- Se debe implementar la funcion checkProgram que
-- dado un AST que representa un programa
-- retorna una lista de mensajes de error en caso
-- de que el programa no sea correcto o, en otro caso,
-- un ambiente (Env) con las variables (y sus tipos) 
-- que se declaran en el programa.  


module TypeChecker where

import Syntax
-- se pueden agregar mas importaciones 
-- en caso de ser necesario



-- TYPE CHECKER

data Error = Duplicated      Name
           | Undefined       Name
           | NotArray        Type
           | ArrayAssignment Type
           | Expected        Type Type

instance Show Error where
 show (Duplicated      n)  = "Duplicated definition: " ++ n
 show (Undefined       n)  = "Undefined: " ++ n
 show (NotArray        ty) = "Type " ++ show ty ++ " is not an array" 
 show (ArrayAssignment ty) = "Array assignment: " ++ show ty
 show (Expected    ty ty') = "Expected: " ++ show ty ++ " Actual: " ++ show ty'
 


-- devuelve todas las variables (variable, tipo) 
variables :: [VarDef] -> Env
variables xs = [(x,y) | (VarDef x y) <- xs]

-- chequea si la variable Name pertenece a la lista de variables (que se pasa) ya chequeadas en el caso de los duplicados
pertenece :: [VarDef] -> Name -> Bool
pertenece xs nombre | [x | (VarDef x y) <- xs, x == nombre] /= [] = True 
                    | otherwise                                   = False
                  
-- devuelve el nombre de la tupla
primerElem :: VarDef -> Name
primerElem (VarDef x y) = x;

devolverTipo :: Env -> Name -> Type
devolverTipo (x:xs) nombre | fst(x) == nombre = snd(x)
                           | otherwise        = devolverTipo xs nombre

-- se manejan 2 listas la de visitados y no visitados, cada vez que se pasa una variable a la lista de visitados se chequea que no este duplicada, en caso de estarlo se tira el error
duplicados :: [VarDef] -> [VarDef] -> [Error]
duplicados xs [] = []
duplicados xs ys |(pertenece xs (primerElem(head(ys)))) = [Duplicated (primerElem(head(ys)))] ++ duplicados(xs ++ ([head(ys)])) (tail(ys)) 
                 | otherwise                            = duplicados(xs ++ ([head(ys)])) (tail(ys))
                 
nombreVariable :: Expr -> Name
nombreVariable (Var nom xs) = nom
                 
-- Pasamos a utilizar 2 iteradores, uno para los enteros y otro para los booleanos, es decir, cuando leemos un operador unario o binario, dependiendo
-- de cual sea; la subexpresiones relacionados a este deben ser un boolean o un integer para ser correcta, 
-- medio obscuro pero es lo que esta saliendo :)

--itera sobre la expresion que se le pasa,, pero espera obtener un booleano
iteracionSublistasBool :: Expr -> [VarDef] -> [Error]
iteracionSublistasBool x defs =
         case x of
            Var nom cola ->
                                    if (pertenece defs nom) -- si pertenece a las variables ya declaradas
                                    then
                                        if ((devolverTipo (variables(defs)) nom) == TyInt) -- si es un integer
                                        then 
                                                if length(cola) == 0
                                                then
                                                    [Expected TyBool TyInt]
                                                else
                                                    [NotArray TyInt]
                                        else                                  
                                            if (devolverTipo (variables(defs)) nom) == TyBool
                                            then 
                                                if length(cola) == 0
                                                then
                                                    []
                                                else
                                                    [NotArray TyBool]
                                            else
                                                if length(expresionValida (Var nom cola) defs) == 0
                                                then
                                                    if (largoArray (devolverTipo (variables(defs)) nom) 0) > length cola
                                                    then
                                                        [Expected TyBool (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                    else
                                                        if (largoArray (devolverTipo (variables(defs)) nom) 0) == length cola then
                                                            if tipoArray (length cola) (devolverTipo (variables(defs)) nom) == TyBool
                                                            then
                                                                []
                                                            else
                                                                [Expected TyBool (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                        else
                                                            if (largoArray (devolverTipo (variables(defs)) nom) 0) < length cola
                                                            then
                                                                [NotArray (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                            else
                                                                []
                                                        
                                                    
                                                else
                                                    expresionValida (Var nom cola) defs
                                                    
                                    else [Undefined (nom)]
            IntLit  int ->
                                [Expected TyBool TyInt]   
            BoolLit b ->
                                []
            Unary ue cola ->
                                case ue of
                                    Not ->
                                            if length(iteracionSublistasBool cola defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    []
                                            else
                                                    (iteracionSublistasBool cola defs) -- si hay error en la subexpresion tiro esos errores y 
                                                    -- el error del while nunca llegaria a ejecutarse porque muere antes (creo)
                                    Neg ->  
                                            if length(iteracionSublistasInt cola defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    [Expected TyBool TyInt] -- si no tiene errores es que debe ser un int la subexp, devuelvo error vacio entonces
                                            else
                                                    (iteracionSublistasInt cola defs)
            Binary bexp e1 e2 ->                    
                                case bexp of -- aca se toma ventaja de la chanchada que hice con las sublistas
                                    Or ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    And ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Equ ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    []
                                                else
                                                    iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Less ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    []
                                                else
                                                    iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Plus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyBool TyInt]
                                                else
                                                    iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Minus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyBool TyInt]
                                                else
                                                    iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mult ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyBool TyInt]
                                                else
                                                    iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Div -> 
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyBool TyInt]
                                                else
                                                    iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mod ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyBool TyInt]
                                                else
                                                    iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs


tipoArray :: Int -> Type -> Type
tipoArray int tipo =
        if int > 0
        then
            case tipo of
                TyInt ->
                    TyInt    
                        
                TyBool ->
                    TyBool
                TyArray int1 int2 tip ->
                    tipoArray (int-1) tip
        else
            tipo
                                                
                                                

chequeoArray :: (Expr) -> Type -> Type ->[VarDef] -> [Error]
chequeoArray exp tipo tipo2 defs = 
        case exp of
            Var nom cola ->
                        if (tipo == TyInt) -- si es un integer
                        then 
                            if length(cola) == 1
                            then
                                    iteracionSublistasInt (head(cola)) defs
                            else
                                    if length(cola) > 1
                                    then
                                        if length(iteracionSublistasInt (head(cola)) defs) == 0
                                        then
                                            if length(iteracionSublistasInt (head(tail(cola))) defs) == 0
                                            then
                                                [NotArray TyInt]
                                            else
                                                iteracionSublistasInt (head(tail(cola))) defs
                                        else
                                            iteracionSublistasInt (head(cola)) defs
                                    else
                                        []
                        else                                  
                            if tipo == TyBool
                            then 
                                if length(cola) == 1
                                then
                                    iteracionSublistasInt (head(cola)) defs
                                else
                                    if length (cola) > 1
                                    then
                                        if length(iteracionSublistasInt (head(cola)) defs) == 0
                                        then
                                            if length (iteracionSublistasInt (head(tail(cola))) defs) == 0
                                            then
                                                [NotArray TyBool]
                                            else
                                                iteracionSublistasInt (head(tail(cola))) defs
                                        else
                                            iteracionSublistasInt (head(cola)) defs
                                    else
                                        []
                                    
                            else
                                case tipo of -- arreglos multidimensionales hay q hacerlo bien
                                    TyArray int1 int2 tip ->   
                                                    if length cola == 0 
                                                    then
                                                         []
                                                    else
                                                        if length(iteracionSublistasInt (head(cola)) defs) == 0
                                                        then
                                                            chequeoArray(Var nom (tail(cola))) tipo tip defs
                                                        else
                                                            iteracionSublistasInt (head(cola)) defs
                                                            
largoArray :: Type -> Int -> Int
largoArray tipo int =
                if (tipo == TyInt) -- si es un integer
                then 
                    int
                else if (tipo == TyBool)
                     then
                        int
                     else
                        case tipo of
                            TyArray ini fin tip2 ->
                                    largoArray tip2 (int+1)
                                                
chequeoIndices :: [Expr] -> Int -> [VarDef] -> [Error]
chequeoIndices exp indice defs =
                    if indice > 0
                    then
                        if length(iteracionSublistasInt (head exp) defs) == 0 
                        then    
                            chequeoIndices (tail exp) (indice -1) defs
                        else
                            iteracionSublistasInt (head exp) defs
                    else
                        []

iterSublistasLista :: [Expr] -> [VarDef] -> [Error]
iterSublistasLista [] defs = []
iterSublistasLista xs defs = iteracionSublistasInt (head(xs)) defs ++ iterSublistasLista (tail xs) defs
                        
                        
expresionValida :: Expr -> [VarDef] -> [Error]
expresionValida exp defs = 
        case exp of
            Var nom cola -> 
                            if (pertenece defs nom) -- si pertenece a las variables ya declaradas
                            then
                                if ((devolverTipo (variables(defs)) nom) == TyInt) -- si es un integer
                                        then 
                                                chequeoIndices cola (length cola) defs
                                        else                                  
                                            if (devolverTipo (variables(defs)) nom) == TyBool
                                            then 
                                                chequeoIndices cola (length cola) defs
                                                
                                            else
                                                chequeoIndices cola (length cola) defs
                                                
                            else [Undefined (nom)]
            IntLit  int ->
                            []
            BoolLit b ->
                            []
            Unary   u exp ->
                            case u of
                                Not  ->
                                        if length(iteracionSublistasBool exp defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    [] 
                                            else
                                                    (iteracionSublistasBool exp defs) -- si hay error en la subexpresion tiro esos errores y 
                                                    -- el error del while nunca llegaria a ejecutarse porque muere antes (creo)
                                Neg ->  
                                            if length(iteracionSublistasInt exp defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    [] -- si no tiene errores es que debe ser un int la subexp, devuelvo error vacio entonces
                                            else
                                                    (iteracionSublistasInt exp defs) 
            Binary  b e1 e2 ->
                                case b of -- aca se toma ventaja de la chanchada que hice con las sublistas
                                    Or ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    And ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Equ ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Less ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Plus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Minus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mult ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Div -> 
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mod ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs 
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs




--itera sobre la expresion que se le pasa, pero siempre esperando obtener un integer                 
iteracionSublistasInt :: Expr -> [VarDef] -> [Error]
iteracionSublistasInt x defs = 
         case x of  
            Var nom cola -> 
                                    if (pertenece defs nom) -- si pertenece a las variables ya declaradas
                                    then
                                        if ((devolverTipo (variables(defs)) nom) == TyInt) -- si es un integer
                                        then 
                                                if length(cola) == 0
                                                then
                                                    []
                                                else
                                                    [NotArray TyInt]
                                        else                                  
                                            if (devolverTipo (variables(defs)) nom) == TyBool
                                            then 
                                                if length(cola) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    [NotArray TyBool]
                                            else
                                                if length(expresionValida (Var nom cola) defs) == 0
                                                then
                                                    if (largoArray (devolverTipo (variables(defs)) nom) 0) > length cola
                                                    then
                                                        [Expected TyInt (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                    else
                                                        if (largoArray (devolverTipo (variables(defs)) nom) 0) == length cola then
                                                            if tipoArray (length cola) (devolverTipo (variables(defs)) nom) == TyInt
                                                            then
                                                                []
                                                            else
                                                                [Expected TyInt (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                        else
                                                            if (largoArray (devolverTipo (variables(defs)) nom) 0) < length cola
                                                            then
                                                                [NotArray (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                            else
                                                                []
                                                        
                                                    
                                                else
                                                    expresionValida (Var nom cola) defs
                                                    
                                    else [Undefined (nom)]

            IntLit num ->  
                                    []
            BoolLit b ->
                                    ([Expected TyInt TyBool]) -- hay que verlo mas a fondo
            Unary ue cola ->
                                case ue of
                                    Not ->
                                            if length(iteracionSublistasBool cola defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    [Expected TyInt TyBool] 
                                            else
                                                    (iteracionSublistasBool cola defs) -- si hay error en la subexpresion tiro esos errores y 
                                                    -- el error del while nunca llegaria a ejecutarse porque muere antes (creo)
                                    Neg ->  
                                            if length(iteracionSublistasInt cola defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    [] -- si no tiene errores es que debe ser un int la subexp, devuelvo error vacio entonces
                                            else
                                                    (iteracionSublistasInt cola defs) 
            Binary bexp e1 e2 ->                    
                                case bexp of -- aca se toma ventaja de la chanchada que hice con las sublistas
                                    Or ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    And ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Equ ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Less ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Plus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Minus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mult ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Div -> 
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mod ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    
                                  
                                               

recursionWrite :: Expr -> [VarDef] -> [Error]
recursionWrite x defs = 
        case x of 
            Var nom cola ->
                                if (pertenece defs nom) -- si pertenece a las variables ya declaradas
                                    then
                                        if ((devolverTipo (variables(defs)) nom) == TyInt) -- si es un integer
                                        then 
                                                if length(cola) == 0
                                                then
                                                    []
                                                else
                                                    [NotArray TyInt]
                                        else                                  
                                            if (devolverTipo (variables(defs)) nom) == TyBool
                                            then 
                                                if length(cola) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    [NotArray TyBool]
                                            else
                                                if length(expresionValida (Var nom cola) defs) == 0
                                                then
                                                    if (largoArray (devolverTipo (variables(defs)) nom) 0) > length cola
                                                    then
                                                        [Expected TyInt (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                    else
                                                        if (largoArray (devolverTipo (variables(defs)) nom) 0) == length cola then
                                                            if tipoArray (length cola) (devolverTipo (variables(defs)) nom) == TyInt
                                                            then
                                                                []
                                                            else
                                                                [Expected TyInt (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                        else
                                                            if (largoArray (devolverTipo (variables(defs)) nom) 0) < length cola
                                                            then
                                                                [NotArray (tipoArray (length cola) (devolverTipo (variables(defs)) nom))]
                                                            else
                                                                []
                                                        
                                                    
                                                else
                                                    expresionValida (Var nom cola) defs
                                                    
                                    else [Undefined (nom)] 
            IntLit  int ->
                                [] -- integer solo, no tengo problemas y tiro error vacio
            BoolLit b ->
                                [Expected TyInt TyBool]
            Unary ue cola ->
                                case ue of
                                    Not ->
                                            if length(iteracionSublistasBool cola defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    [Expected TyInt TyBool] -- si no tiene errores tiro el error por esperar un int del write y doy un bool
                                            else
                                                    (iteracionSublistasBool cola defs) -- si hay error en la subexpresion tiro esos errores y 
                                                    -- el error del while nunca llegaria a ejecutarse porque muere antes (creo)
                                    Neg ->  
                                            if length(iteracionSublistasInt cola defs) == 0 -- chequeo que la subexpresion no tenga errores
                                            then
                                                    [] -- si no tiene errores es que debe ser un int la subexp, devuelvo error vacio entonces
                                            else
                                                    (iteracionSublistasInt cola defs) -- si hay error en la subexpresion tiro esos errores y 
                                                    

            Binary  bexp e1 e2 ->                    
                                case bexp of -- aca se toma ventaja de la chanchada que hice con las sublistas
                                    Or ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    And ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Equ ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Less ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                if length(iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs) == 0
                                                then
                                                    [Expected TyInt TyBool]
                                                else
                                                    iteracionSublistasBool e1 defs ++ iteracionSublistasBool e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Plus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Minus ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mult ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Div -> 
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                    Mod ->
                                            if length(expresionValida e1 defs ++ expresionValida e2 defs) == 0
                                            then
                                                iteracionSublistasInt e1 defs ++ iteracionSublistasInt e2 defs
                                            else
                                                expresionValida e1 defs ++ expresionValida e2 defs
                                

recursionAsignacion :: Name -> [Expr] -> Expr -> [VarDef] -> [Error]
recursionAsignacion nom expa exp2 defs =
        if (pertenece defs nom)
        then
                if ((devolverTipo (variables(defs)) nom) == TyInt) -- variable a la izquierda es int
                then
                        if length(expa) == 0 -- si tiene largo 0 es un entero nom, entonces se manda a confirmar q la exp2 sea un entero
                        then
                            iteracionSublistasInt exp2 defs 
                        else -- se deben chequear q no haya errores en todas las subexpresiones, sino se tira el siguiente error, ejem x integer y tenemos x[u+y]
                            -- si no tiene error !!!!! hay q arreglarlo
                            if length (iterSublistasLista expa defs ) == 0 then
                                [NotArray TyInt] ++ expresionValida exp2 defs 
                            else
                                iterSublistasLista expa defs ++ iteracionSublistasInt exp2 defs
                else
                    if ((devolverTipo (variables(defs)) nom) == TyBool) --variable a la izquierda es bool
                    then
                        if length(expa) == 0 -- si tiene largo 0 es un boolean nom, entonces se manda a confirmar q la exp2 sea un boolean
                        then
                            iteracionSublistasBool exp2 defs 
                        else -- se deben chequear q no haya errores en todas las subexpresiones, sino se tira el siguiente error, ejem x integer y tenemos x[u+y]
                            -- si no tiene error !!!!! hay q arreglarlo
                            if length (iterSublistasLista expa defs ) == 0 then
                                [NotArray TyBool] ++ iteracionSublistasInt exp2 defs 
                            else
                                iterSublistasLista expa defs ++ iteracionSublistasInt exp2 defs
                    else -- es un array
                        if length expa == 0 then
                            if length (iteracionSublistasInt exp2 defs) == 0 
                            then
                                [ArrayAssignment (devolverTipo (variables(defs)) nom)] ++ expresionValida exp2 defs
                            else
                                [ArrayAssignment (devolverTipo (variables(defs)) nom)] ++ expresionValida exp2 defs
                        else
                            if length (expresionValida exp2 defs) == 0 then
                                if length (expresionValida (Var nom expa) defs) /= 0 then
                                    expresionValida (Var nom expa) defs
                                else
                                    if (largoArray (devolverTipo (variables(defs)) nom) 0) > length expa
                                    then
                                        [ArrayAssignment (tipoArray (length expa) (devolverTipo (variables(defs)) nom))]
                                    else
                                        if (largoArray (devolverTipo (variables(defs)) nom) 0) == length expa 
                                        then
                                            if tipoArray (length expa) (devolverTipo (variables(defs)) nom) == TyInt
                                            then
                                                iteracionSublistasInt exp2 defs
                                            else
                                                iteracionSublistasBool exp2 defs
                                        else
                                            if (largoArray (devolverTipo (variables(defs)) nom) 0) < length expa
                                            then
                                                [NotArray (tipoArray (length expa) (devolverTipo (variables(defs)) nom))]
                                            else
                                                []
                                    
                            else
                                if length (expresionValida (Var nom expa) defs) /= 0 then
                                    expresionValida (Var nom expa) defs ++ expresionValida exp2 defs
                                else
                                    
                                        if (largoArray (devolverTipo (variables(defs)) nom) 0) > length expa
                                        then
                                            [ArrayAssignment (tipoArray (length expa) (devolverTipo (variables(defs)) nom))] ++ expresionValida exp2 defs
                                        else
                                            if (largoArray (devolverTipo (variables(defs)) nom) 0) == length expa 
                                            then
                                                if tipoArray (length expa) (devolverTipo (variables(defs)) nom) == TyInt
                                                then
                                                    iteracionSublistasInt exp2 defs
                                                else
                                                    iteracionSublistasBool exp2 defs
                                            else
                                                if (largoArray (devolverTipo (variables(defs)) nom) 0) < length expa
                                                then
                                                    [NotArray (tipoArray (length expa) (devolverTipo (variables(defs)) nom))] ++ expresionValida exp2 defs
                                                else
                                                    expresionValida exp2 defs -- nunca se llega a esta linea
                                    
                                
                                

                                           
        else [Undefined nom] ++ expresionValida exp2 defs
                                
erroresCuerpo :: [Stmt] -> [VarDef] -> [Error]
erroresCuerpo [] defs = []
erroresCuerpo (x:xs) defs = 
    case x of
        Asig  nom expa exp2 ->
            recursionAsignacion nom expa exp2 defs ++ erroresCuerpo xs defs
            
            
        If exp body1 body2 ->
            iteracionSublistasBool exp defs ++ erroresCuerpo body1 defs ++ erroresCuerpo body2 defs ++ erroresCuerpo xs defs
        
        For nom expa exp2 body ->
            if (pertenece defs nom)
            then
                if ((devolverTipo (variables(defs)) nom) == TyInt)
                then
                    iteracionSublistasInt expa defs ++ iteracionSublistasInt exp2 defs ++ erroresCuerpo body defs ++ erroresCuerpo xs defs
                else
                    [Expected TyInt (devolverTipo (variables(defs)) nom)] ++ iteracionSublistasInt expa defs ++ iteracionSublistasInt exp2 defs ++ erroresCuerpo body defs ++ erroresCuerpo xs defs
            else [Undefined nom] ++ iteracionSublistasInt exp2 defs ++ erroresCuerpo body defs ++ erroresCuerpo xs defs
            
        While exp body ->
            iteracionSublistasBool exp defs ++ erroresCuerpo body defs ++ erroresCuerpo xs defs
            
        Write exp ->
            recursionWrite exp defs ++ erroresCuerpo xs defs 
                
        Read nom ->
            if (pertenece defs nom)
            then
                if ((devolverTipo (variables(defs)) nom) == TyInt)
                then (erroresCuerpo xs defs)
                else
                    ([Expected TyInt (devolverTipo (variables(defs)) nom)] ++ erroresCuerpo xs defs)
            else ([Undefined nom] ++ erroresCuerpo xs defs)  -- read solo puede tomar enteros, : no funciona?
                                                                                        
 
--Por ahora solo se chequea que no se encuentren duplicados de definiciones en las variables

checkProgram :: Program -> Either [Error] Env
checkProgram (Program pn defs body) = 
                                        if length(duplicados [] defs) == 0 
                                        then 
                                                if length(erroresCuerpo body defs) == 0
                                                then 
                                                    Right (variables defs)
                                                else
                                                    Left (erroresCuerpo body defs)
                                        else
                                                Left (duplicados [] defs)










