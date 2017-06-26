-- LABORATORIO DE PROGRAMACION FUNCIONAL 2017
-- MODULO DE GENERACION DE CODIGO C

-- Se debe implementar la funcion genProgram que
-- dado un AST que representa un programa valido
-- genera el codigo C correspondiente


module Generator where

import Syntax
-- se pueden agregar mas importaciones 
-- en caso de ser necesario


-- CODE GENERATOR

--Genera el programa
genProgram :: Program -> String
genProgram (Program dfs expr) = printInclude ++ genDefs dfs ++ genMain expr

--Genera la expresion del MAIN
genMain :: Expr -> String
genMain expr = printMainHead ++ toStr0 expr ++ printMainTail

genDefs :: Defs -> String
genDefs [] = ""
genDefs (f:funs) = show f ++ genDefs funs

instance Show FunDef where
    show (FunDef typedFun names expr) =  showTypedFun typedFun ++ "(" ++ showParams names ++ "){" ++ showLets (lets) 0 ++ "\nreturn (" ++ toStr0 expr ++ "); };\n"
      where lets = getLets expr

showTypedFun :: TypedFun -> String
showTypedFun (name, sig) = show sig ++ "_" ++ name

showParams :: [Name] -> String
showParams []   = ""
showParams (name:names)  = "int _" ++ name ++ (concat $ map (",int _"++) names)

showLets :: [Expr] -> Int -> String
showLets [] _ = ""
showLets (expr:exprs) i = "\n" ++ fst rec ++ showLets exprs (1+snd rec)
  where rec = showFuncLet expr i

showFuncLet :: Expr-> Int -> (String,Int) --Case de let in exp1 o exp2
showFuncLet (Let typedVar expr1 expr2) i = ("int _let" ++ show (i+length(lets)) ++ "(int _" ++ fst (typedVar) ++ "){" ++ showLets lets i ++ "\nreturn (" ++ fst (toStr expr2 i)++ "); };" , i+length(lets) )
 where lets = getLets expr2

--Obtiene los Lets de primer nivel (no recursivos)
getLets :: Expr -> [Expr]
getLets (Infix op expr1 expr2) = getLets(expr1) ++ getLets(expr2)
getLets (If expr1 expr2 expr3) = getLets(expr1) ++ getLets(expr2) ++ getLets(expr3)
getLets (Let typedVar expr1 expr2) = getLets(expr1) ++ [(Let typedVar expr1 expr2)] -- <- me quedo con los let del LET, no los del IN
getLets (App name (expr:exprs)) = getLets(expr)++ getLets (App name exprs)
getLets (App name []) = []
getLets _ = []

instance Show Sig where
    show (Sig types typ) = showType typ

--Genera las expresiones
toStr0 :: Expr -> String
toStr0 e = fst res
  where res =  toStr e 0

--Genera las expresiones, con un contador de lets
toStr :: Expr -> Int -> (String, Int)
toStr (Var name) i = ("_" ++ name, i)
toStr (IntLit integer) i = (show (integer), i)
toStr (BoolLit bool) i | bool = ("1", i) | otherwise = ("0", i)
toStr (Infix op expr1 expr2) i =  ("(" ++ fst resExp1 ++ show op ++ fst resExp2 ++ ")", (snd resExp2))
  where resExp1 =  toStr expr1 i
        resExp2 =  toStr expr2 (snd resExp1)
toStr (If expr1 expr2 expr3) i =  ("" ++ fst resExp1 ++ "?" ++ fst resExp2 ++ ":" ++ fst resExp3 ++ "", (snd resExp3))
  where resExp1 =  toStr expr1 i
        resExp2 =  toStr expr2 (snd resExp1)
        resExp3 =  toStr expr3 (snd resExp2)
toStr (Let typedVar expr1 expr2) i = (  "_let"++ show (snd resExp2) ++"(" ++ fst resExp1 ++")", (snd resExp2)+1) --TODO WIP
  where resExp1 =  toStr expr1 i --(i + length getLets expr1)
        resExp2 =  toStr expr2 (snd resExp1)
toStr (App name exprs) i = ( "_" ++ name ++ "("  ++ fst resExp ++ ")", (snd resExp))
  where resExp =  toStrParam exprs i

--Gernera las expresiones de los parametros de las funciones
toStrParam :: [Expr] -> Int -> (String, Int)
toStrParam exprs i | null exprs = ("",i)
                   | length exprs == 1 = pre
                   | otherwise = ( fst pre ++ "," ++ fst post , snd post)
  where pre =  toStr (head exprs) i
        post =  toStrParam (tail exprs) (snd pre)

--Genera el operador
instance Show Op where
    show Add = " + "
    show Sub = " - "
    show Mult = " * "
    show Div = " / "
    show Eq = "=="
    show NEq = "!="
    show GTh = ">"
    show LTh = "<"
    show GEq = ">="
    show LEq = "<="

--Genera el tipo
showType :: Type -> String
showType (TyInt) = "int "
showType (TyBool) = "int "

--Constantes en el codigo
printInclude = "#include <stdio.h>\n"
printMainHead= "int main() {\nprintf(\"%d\\n\","
printMainTail= "); }\n"