-- LABORATORIO DE PROGRAMACION FUNCIONAL 2016
-- MODULO DE GENERACION DE CODIGO C

-- Se debe implementar la funcion genProgram que
-- dado un AST que representa un programa valido
-- y un ambiente con las variables definidas
-- genera el codigo C correspondiente


module Generator where

import Syntax

-- se pueden agregar mas importaciones 
-- en caso de ser necesario


-- CODE GENERATOR
devolverTipo2 :: Env -> Name -> Type
devolverTipo2 (x:xs) nombre | fst(x) == nombre = snd(x)
                           | otherwise        = devolverTipo2 xs nombre
                           
variables2 :: [VarDef] -> Env
variables2 xs = [(x,y) | (VarDef x y) <- xs]                           


stringArray :: Integer -> Integer -> Type -> String
stringArray inicio fin tipo =
                    case tipo of
                        TyInt ->
                                "[" ++ show (fin - inicio + 1) ++ "]"
                        TyBool ->
                                "[" ++ show (fin - inicio + 1) ++ "]"
                        TyArray ini fin2 tip ->  
                                "[" ++ show (fin - inicio + 1) ++ "]" ++ stringArray ini fin2 tip

stringVar :: Env -> String
stringVar env = if length env /= 0
                then
                    case (snd(head(env))) of
                            TyInt ->
                                    "int _" ++ fst(head(env)) ++ ";\n" ++ stringVar (tail(env))
                            TyBool ->
                                    "int _" ++ fst(head(env)) ++ ";\n" ++ stringVar (tail(env))
                            TyArray ini fin tipo ->        
                                    "int _" ++ fst(head(env)) ++ stringArray ini fin tipo ++ ";\n" ++ stringVar (tail(env))
                else
                    ""

indicesArreglo :: [VarDef] -> Int -> [Expr] -> Integer -> Type -> String
indicesArreglo de int exp ini tipo =
                if int > 0
                then
                    case tipo of
                        TyInt ->
                                "[" ++ expString de [head(exp)] ++ " - " ++ show ini ++ "]"
                        TyBool ->
                                "[" ++ expString de [head(exp)] ++ " - " ++ show ini ++ "]"
                        TyArray inicio fin tip ->        
                            "[" ++ expString de [head(exp)] ++ " - " ++ show ini ++ "]" ++ indicesArreglo de (int - 1) (tail(exp)) inicio tip
                else
                    ""
                    
expString :: [VarDef] -> [Expr] -> String
expString defs [] = ""
expString defs (x:xs)  =
            case x of
            Var nom cola -> 
                            case((devolverTipo2 (variables2(defs)) nom)) of
                                TyArray ini fin tipo ->
                                    "_" ++ nom ++ indicesArreglo defs (length cola) cola ini tipo
                                TyInt ->
                                    "_" ++ nom ++ indicesArreglo defs (length cola) cola 0 TyInt
                                TyBool ->
                                    "_" ++ nom ++ indicesArreglo defs (length cola) cola 0 TyBool
            IntLit int ->
                            show int
            BoolLit b ->
                            if b then
                                show 1
                            else
                                show 0
            Unary u e ->
                            case u of
                                Not  ->
                                        "! " ++ expString defs [e]
                                Neg ->  
                                        "- " ++ expString defs [e]
            Binary b e1 e2 ->
                            case b of -- aca se toma ventaja de la chanchada que hice con las sublistas
                                    Or ->
                                            "(" ++ expString defs [e1] ++ " || " ++ expString defs [e2] ++ ")"
                                    And ->
                                            "(" ++ expString defs [e1] ++ " && " ++ expString defs [e2] ++ ")"
                                    Equ ->
                                            "(" ++ expString defs [e1] ++ " == " ++ expString defs [e2] ++ ")"
                                    Less ->
                                            "(" ++ expString defs [e1] ++ " < " ++ expString defs [e2] ++ ")"
                                    Plus ->
                                            "(" ++ expString defs [e1] ++ " + " ++ expString defs [e2] ++ ")"
                                    Minus ->
                                            "(" ++ expString defs [e1] ++ " - " ++ expString defs [e2] ++ ")"
                                    Mult ->
                                            "(" ++ expString defs [e1] ++ " * " ++ expString defs [e2] ++ ")"
                                    Div -> 
                                            "(" ++ expString defs [e1] ++ " / " ++ expString defs [e2] ++ ")"
                                    Mod ->
                                            "(" ++ expString defs [e1] ++ " % " ++ expString defs [e2] ++ ")"


                    
stringBody :: [VarDef] -> [Stmt]-> Env -> String
stringBody defs [] env = ""
stringBody defs (x:xs) env = 
                case x of -- x es body
                    Asig nom exp1 exp2 ->
                          expString defs [(Var nom exp1)] ++ " = " ++ expString defs [exp2] ++ ";\n" ++ stringBody defs xs env
                    If exp body1 body2 ->
                            case exp of
                                BoolLit b ->
                                    "if " ++ "(" ++ expString defs [exp] ++ ")" ++ "{\n" ++ stringBody defs body1 env ++ "}else{\n" ++ stringBody defs body2 env ++ "};\n" ++ stringBody defs xs env
                                Var nom cola -> 
                                    "if " ++ "(" ++ expString defs [exp] ++ ")" ++ "{\n" ++ stringBody defs body1 env ++ "}else{\n" ++ stringBody defs body2 env ++ "};\n" ++ stringBody defs xs env
                                IntLit int ->
                                    "if " ++ "(" ++ expString defs [exp] ++ ")" ++ "{\n" ++ stringBody defs body1 env ++ "}else{\n" ++ stringBody defs body2 env ++ "};\n" ++ stringBody defs xs env
            
                                Unary u e ->
                                    "if " ++ "(" ++ expString defs [exp] ++ ")" ++ "{\n" ++ stringBody defs body1 env ++ "}else{\n" ++ stringBody defs body2 env ++ "};\n" ++ stringBody defs xs env
                                Binary b e1 e2 ->
                                    "if " ++ "(" ++ expString defs [exp] ++ ")" ++ "{\n" ++ stringBody defs body1 env ++ "}else{\n" ++ stringBody defs body2 env ++ "};\n" ++ stringBody defs xs env
                    For nom exp1 exp2 body ->
                            "for (" ++ "_" ++ nom ++ "=" ++ expString defs [exp1] ++ ";" ++ "_" ++ nom ++ " <= " ++ expString defs [exp2] ++ ";" ++ "_" ++ nom ++ "++){\n" ++ stringBody defs body env ++ "};\n" ++ stringBody defs xs env
                    While exp body ->
                            "while (" ++ expString defs [exp] ++ "){\n" ++ stringBody defs body env ++ "};\n" ++ stringBody defs xs env
                    Write exp ->
                            "printf (\"%d\\n\"," ++ expString defs [exp] ++ ");\n" ++ stringBody defs xs env
                    Read name ->
                            "scanf (\"%d\", &_" ++ name ++ ");\n" ++ stringBody defs xs env




genProgram :: Program -> Env -> String
genProgram (Program pn dfs bdy) env = "#include <stdio.h>\n" ++ stringVar env ++ "void main() {\n" ++ stringBody dfs bdy env ++ "}"
