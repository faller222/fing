-- LABORATORIO DE PROGRAMACION FUNCIONAL 2017
-- MODULO DE CHEQUEO

-- Se debe implementar la funcion checkProgram que
-- dado un AST que representa un programa
-- retorna una lista de mensajes de error en caso
-- de que el programa no sea correcto u Ok en otro caso.  


module Checker where

import Syntax
-- se pueden agregar mas importaciones 
-- en caso de ser necesario


-- CHECKER

data Checked = Ok | Wrong [Error]

data Error = Duplicated      Name
           | Undefined       Name
           | ArgNumDef       Name Int Int
           | ArgNumApp       Name Int Int
           | Expected        Type Type
            
instance Show Error where
 show (Duplicated      n)  = "Duplicated declaration: " ++ n
 show (Undefined       n)  = "Undefined: " ++ n
 show (ArgNumDef   n s d)  = "The number of parameters in the definition of " ++ n ++
                             " doesn't match the signature ("++ show d ++ " vs " ++ show s ++ ")"
 show (ArgNumApp   n s d)  = "The number of arguments in the application of: " ++ n ++
                             " doesn't match the signature ("++ show d ++ " vs " ++ show s ++ ")"
 show (Expected    ty ty') = "Expected: " ++ show ty ++ " Actual: " ++ show ty'


checkProgram :: Program -> Checked
checkProgram (Program defs body) = snd(ej9 +++(ej8 +++( ej7 +++ (p,ej6 p))))
                                    where
                                        p = (Program defs body)

--Operador para concatenar chequeos
(+++) ::  (Program -> Checked)-> (Program , Checked) -> (Program , Checked)
(+++) f (Program defs body, Ok) = (Program defs body, f (Program defs body))
(+++) _ (Program defs body, Wrong errores) = (Program defs body, Wrong errores)

--Funcion para encapsular errores
checked :: [Error] -> Checked
checked errores | null errores = Ok
                | otherwise = Wrong (errores)

--Ejemplo 6
ej6 :: Program -> Checked
ej6 (Program defs body) = checked (checkDuplicatedNames (getFuncNames(defs))++ checkDuplicatedParams (defs))

--Ejemplo 7
ej7 :: Program -> Checked
ej7 (Program defs body) = checked (comparacionErrores (defs))

--Ejemplo 8
ej8 :: Program -> Checked
ej8 (Program defs body) = checked (checkUndefined (Program defs body))


--Ejemplo 8
ej9 :: Program -> Checked
ej9 (Program defs body) = checked (checkTypes (Program defs body)) --checked ( => checkUndefinedfunciondechecquerej9 (Program defs body))


--Verifica que no haya elementos duplicados en la lista de nombres
checkDuplicatedNames :: [Name] -> [Error]
checkDuplicatedNames name   | null name = []
                            | primero `elem` resto = Duplicated (primero) : checkDuplicatedNames (resto)
                            | otherwise = checkDuplicatedNames (resto)
                             where
                                primero = head name
                                resto = tail name

getFuncNames :: Defs -> [Name]
getFuncNames defs =  [ getFuncName x | x <- defs ]


getFuncName :: FunDef -> Name
getFuncName (FunDef typedFun names expr) = fst typedFun

getFuncParam :: FunDef -> [Name]
getFuncParam (FunDef typedFun names expr) = names

checkDuplicatedParams :: Defs -> [Error]
checkDuplicatedParams defs  | null defs = []
                            | otherwise =  checkDuplicatedNames(getFuncParam( head(defs) )) ++ checkDuplicatedParams(tail(defs))



getFuncParTypes :: FunDef -> Sig
getFuncParTypes (FunDef typedFun names expr) = snd typedFun

getFuncSigTypesdeParams :: Sig -> [Type]
getFuncSigTypesdeParams (Sig types typ) = types

getFuncSigTypedeReturn :: Sig -> Type
getFuncSigTypedeReturn (Sig types typ) = typ

getFunsigAlista :: Sig -> Int
getFunsigAlista (Sig types typ) = length(types)

comparacionErrores :: [FunDef] -> [Error]
comparacionErrores dfs  | null dfs = []
                        | (cantParam /= cantTypes) =  (ArgNumDef nombre cantTypes cantParam) : comparacionErrores (tail dfs)
                        | otherwise = comparacionErrores (tail dfs)
                    where
                        nombre = getFuncName(head dfs)
                        cantParam = length(getFuncParam (head dfs) )
                        cantTypes = getFunsigAlista (getFuncParTypes(head dfs))

checkUndefined :: Program -> [Error]
checkUndefined (Program defs body) =  (checkUndefinedDefs defs (getNamesFunc defs ))++ (checkUndefinedMain body (getNamesFunc defs ) )


checkUndefinedDefs :: Defs -> [Name] -> [Error]
checkUndefinedDefs dfs defsCompleto  | null dfs = []
                                     | length(dfs)==1 = checkUndefinedFunDef (head dfs)  defsCompleto ++ (checkUndefinedDefs (tail dfs) defsCompleto ) --1212121
                                     | otherwise = (checkUndefinedDefs (tail dfs) defsCompleto)

checkUndefinedFunDef :: FunDef -> [Name] -> [Error]
checkUndefinedFunDef (FunDef typedFun names expr) namesFunc = checkUndefinedVar names expr namesFunc

checkUndefinedVar :: [Name] -> Expr -> [Name] -> [Error]
checkUndefinedVar names (IntLit integer) namesFunc = []
checkUndefinedVar names (BoolLit bool) namesFunc = []
checkUndefinedVar names (Infix op expr1 expr2) namesFunc = (checkUndefinedVar names expr1 namesFunc) ++ (checkUndefinedVar names expr2 namesFunc)
checkUndefinedVar names (If expr1 expr2 expr3) namesFunc = (checkUndefinedVar names expr1 namesFunc) ++ (checkUndefinedVar names expr2 namesFunc) ++ (checkUndefinedVar names expr3 namesFunc)
checkUndefinedVar names (Let typedVar expr1 expr2) namesFunc = (checkUndefinedVar names expr1 namesFunc) ++ (checkUndefinedVar names expr2 namesFunc)
checkUndefinedVar names (App name exprs) namesFunc = (checkUndefinedNameFunc name namesFunc) ++ (checkUndefinedVarExprs names exprs namesFunc)
checkUndefinedVar names (Var name) namesFunc | name `elem` names = []
                                             | otherwise = [Undefined name]

checkUndefinedVarExprs :: [Name] -> [Expr] -> [Name] -> [Error]
checkUndefinedVarExprs names exprs namesFunc  | null exprs = []
                                              | length(exprs)==1 = (checkUndefinedVar names (head exprs) namesFunc) ++ (checkUndefinedVarExprs names (tail exprs) namesFunc)
                                              | otherwise = (checkUndefinedVar names (head exprs) namesFunc)

checkUndefinedNameFunc :: Name -> [Name] -> [Error]
checkUndefinedNameFunc name namesFunc | name `elem` namesFunc = []
                                      | otherwise = [Undefined name]

getNamesFunc :: [FunDef] -> [Name]
getNamesFunc  dfs      | null dfs = []
                       | otherwise = namefunIs (head dfs) : getNamesFunc (tail dfs)

namefunIs :: FunDef -> Name
namefunIs (FunDef typedFun names expr) = fst(typedFun)


checkUndefinedMain :: Expr -> [Name]  -> [Error]
checkUndefinedMain (Var name)  namesFunc = [Undefined name]
checkUndefinedMain (IntLit integer) namesFunc = []
checkUndefinedMain (BoolLit bool) namesFunc = []
checkUndefinedMain (Infix op expr1 expr2) namesFunc =  (checkUndefinedMain expr1 namesFunc) ++ (checkUndefinedMain expr2 namesFunc)
checkUndefinedMain (If expr1 expr2 expr3) namesFunc = (checkUndefinedMain expr1 namesFunc) ++ (checkUndefinedMain expr2 namesFunc) ++ (checkUndefinedMain expr3 namesFunc)
checkUndefinedMain (Let typedVar expr1 expr2) namesFunc =(checkUndefinedMain expr1 namesFunc) ++ (checkUndefinedMain expr2 namesFunc)
checkUndefinedMain (App name exprs) namesFunc = (checkUndefinedNameFunc name namesFunc) ++ (checkUndefinedVarExprsMain exprs namesFunc)

checkUndefinedVarExprsMain :: [Expr] -> [Name] -> [Error]
checkUndefinedVarExprsMain exprs namesFunc  | null exprs = []
                                            | otherwise = (checkUndefinedMain (head exprs) namesFunc) ++ (checkUndefinedVarExprsMain (tail exprs) namesFunc)



-- nueve  --



checkTypes :: Program -> [Error]
checkTypes (Program defs body) =  (checkTypesFunc defs defs )  ++ fst(checkTypesArgsValidos [] body defs)--checkTypesMain defs body --- hacer typesmain


checkTypesFunc :: Defs ->  Defs -> [Error]
checkTypesFunc dfs dfscomp  | null dfs = []
                            | otherwise = ( checkTypesFunDef (head dfs) dfscomp) ++ (checkTypesFunc (tail dfs) dfscomp)


checkTypesFunDef :: FunDef -> Defs -> [Error]
checkTypesFunDef (FunDef typedFun names expr) defs = (comparar (getFuncSigTypedeReturn (getFuncParTypes (FunDef typedFun names expr))) (snd (checkTypesArgsValidos (getTypedParams (FunDef typedFun names expr)) expr defs))) ++   fst(checkTypesArgsValidos (getTypedParams (FunDef typedFun names expr)) expr defs)  -- ++ checkTypesArgsValidos revisarrr

comparar :: Type -> Type -> [Error]
comparar typ1 typ2 | typ1 == typ2 = []
                   | otherwise = [Expected typ1 typ2 ]

getTypedParams:: FunDef -> [(Name,Type)]
getTypedParams (FunDef typedFun names expr) = zip names (getTiposFunc (snd(typedFun)))

getTiposFunc:: Sig -> [Type]
getTiposFunc (Sig pEntradas salida) = pEntradas

checkTypesTypedFun :: TypedFun -> Expr -> Defs  -> [Error]
checkTypesTypedFun typedFun expr defs = (checkTypesSig (snd typedFun) expr defs )

checkTypesSig :: Sig -> Expr -> Defs -> [Error]
checkTypesSig (Sig pEntradas salida) expr defs = (checkTypesTypes salida expr defs)

checkTypesTypes :: Type -> Expr -> Defs -> [Error]
checkTypesTypes (TyBool ) (IntLit integer) dfs = [Expected TyBool TyInt ]
checkTypesTypes (TyInt ) (BoolLit bool)  dfs = [Expected  TyInt TyBool ]
checkTypesTypes (TyBool ) (Infix op expr1 expr2) dfs = case op  of
                                           Add -> [Expected TyBool TyInt ]
                                           Sub -> [Expected TyBool TyInt ]
                                           Mult -> [Expected TyBool TyInt ]
                                           Div -> [Expected TyBool TyInt ]
                                           _ -> []
checkTypesTypes (TyInt ) (Infix op expr1 expr2) dfs = case op  of
                                           Eq -> [Expected  TyInt TyBool]
                                           NEq -> [Expected TyInt TyBool ]
                                           GTh -> [Expected TyInt TyBool ]
                                           LTh -> [Expected TyInt TyBool ]
                                           GEq -> [Expected TyInt TyBool ]
                                           LEq -> [Expected TyInt TyBool ]
                                           _ -> []
checkTypesTypes typ (If expr1 expr2 expr3) dfs= checkTypesTypes  typ expr1 dfs
checkTypesTypes typ (Let typedVar expr1 expr2) dfs = checkTypesTypes  typ expr2 dfs
checkTypesTypes typ (App name exprs) dfs | typ == typeFromName dfs name = []
                                         | otherwise = [Expected typ (typeFromName dfs name) ]


checkTypesTypes _ _ _ = [ ]

typeFromName:: [FunDef] -> Name -> Type
typeFromName [] name =  TyInt -- pisadita
typeFromName (funDefFst:fundfs) name   |  (getFuncName funDefFst) == name = getFuncSigTypedeReturn (getFuncParTypes funDefFst)
                                       |  otherwise = (typeFromName fundfs name)

-- hacer q terminede recorrer para q no tire q no lo reviso

getFuncNamesAndParamLengt :: Defs -> [(Name, Int) ]
getFuncNamesAndParamLengt [] = []
getFuncNamesAndParamLengt (f:fs) = (getFuncName f, length(getFuncParam f)) : getFuncNamesAndParamLengt fs

getCantParam :: Name -> [(Name, Int) ] -> Int
getCantParam name [] =  -1
getCantParam name (h:list) | fst(h)== name = snd(h)
                           | otherwise = getCantParam name list

checkTypeslenghts :: Expr -> [FunDef] -> [Error]
checkTypeslenghts (Var name)  dfsComp  = []
checkTypeslenghts (IntLit integer)  dfsComp  = []
checkTypeslenghts (BoolLit bool)  dfsComp  = []
checkTypeslenghts  (Infix op expr1 expr2)  dfsComp  =(checkTypeslenghts expr1 dfsComp)++ (checkTypeslenghts expr2 dfsComp)
checkTypeslenghts  (If expr1 expr2 expr3)  dfsComp  = (checkTypeslenghts expr1 dfsComp)++ (checkTypeslenghts expr2 dfsComp) ++ (checkTypeslenghts expr3 dfsComp)
checkTypeslenghts  (Let typedVar expr1 expr2)  dfsComp  = (checkTypeslenghts expr1 dfsComp)++ (checkTypeslenghts expr2 dfsComp)
checkTypeslenghts  (App name exprs)   dfsComp       | (cantParam /= cantTypes) =  (ArgNumApp nombre cantTypes cantParam) :  checkTypeslenghtsAux exprs dfsComp -- [(checkTypeslenghts x dfsComp) | x <- exprs]
                                                    | otherwise = []
                                                             where
                                                                      nombre = name
                                                                      cantParam = (length exprs)
                                                                      cantTypes = getCantParam name (getFuncNamesAndParamLengt dfsComp)

checkTypeslenghtsAux :: [Expr] -> [FunDef] -> [Error]
checkTypeslenghtsAux [] _ = []
checkTypeslenghtsAux (expr:exprs) defs =  checkTypeslenghts expr defs ++ checkTypeslenghtsAux exprs defs
--checkTypesMain :: [FunDef] -> Expr   -> ([Error], Type )
--checkTypesMain funDef (Var name)  namesFunc = ([Undefined name],_)
--checkTypesMain funDef (IntLit integer) namesFunc = ([],IntLit)
--checkTypesMain funDef (BoolLit bool) namesFunc = ([],BoolLit)
--checkTypesMain funDef (Infix op expr1 expr2) namesFunc = ( (fst(checkTypesMain funDef expr1) ++ fst(checkTypesMain funDef expr2 ) )  , => type expr corrrecto)
--checkTypesMain funDef (If expr1 expr2 expr3) namesFunc = (  (fst(checkTypesMain funDef expr1) ++ fst(checkTypesMain funDef expr2 ) )  ++ (checkTypesMain funDef expr3 ) , => type expr corrrecto )
--checkTypesMain funDef (Let typedVar expr1 expr2) namesFunc =(checkUndefinedMain expr1 namesFunc) ++ (checkUndefinedMain expr2 namesFunc)
--checkTypesMain funDef (App name exprs) namesFunc = (checkUndefinedNameFunc name namesFunc) ++ (checkUndefinedVarExprsMain exprs namesFunc)

insertLista :: (Name, Type) -> [(Name, Type)] -> [(Name, Type)]
insertLista a [] = [a]
insertLista  (name, tpe ) ((nam, tpee ):t) | name == nam = ((name, tpe ):t)
                                           | otherwise =  (name, tpe ) :((nam, tpee ):t)
getVarFromList :: Name -> [(Name, Type)] -> Type
getVarFromList name ((nam, tpee ):t) | name == nam = tpee
                                     | otherwise =  getVarFromList name t


checkTypesArgsValidos :: [(Name, Type)] -> Expr  -> Defs -> ([Error], Type)
checkTypesArgsValidos list (Var name) defs   = ([],getVarFromList name list)
checkTypesArgsValidos list (IntLit integer) defs  = ([],TyInt)
checkTypesArgsValidos list (BoolLit bool) defs = ([],TyBool)
checkTypesArgsValidos list (Infix op expr1 expr2 ) defs  = case op of
                                                       Add -> case ( snd( checkExp1  ) ,snd( checkExp2  ) )of
                                                                                    (TyInt,TyInt) ->  (fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyInt) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyInt,TyBool) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyBool) ->( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                       Sub -> case ( snd( checkExp1  ) ,snd( checkExp2  ) )of
                                                                                    (TyInt,TyInt) ->  (fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyInt) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyInt,TyBool) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyBool) ->( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                       Mult -> case ( snd( checkExp1  ) ,snd( checkExp2  ) )of
                                                                                    (TyInt,TyInt) ->  (fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyInt) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyInt,TyBool) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyBool) ->( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                       Div -> case ( snd( checkExp1  ) ,snd( checkExp2  ) )of
                                                                                    (TyInt,TyInt) ->  (fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyInt) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyInt,TyBool) -> ( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)
                                                                                    (TyBool,TyBool) ->( [Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyInt)


                                                       Eq -> case ( snd( checkExp1  ) ,snd( checkExp2  ) )of
                                                                                    (TyBool,TyBool) -> (fst( checkExp1 ) ++ fst( checkExp2), TyBool)
                                                                                    (TyInt,TyInt) -> (fst( checkExp1 ) ++ fst( checkExp2), TyBool)
                                                                                    (TyBool,TyInt) -> ([Expected TyBool  TyInt] ++ fst( checkExp1 ) ++ fst( checkExp2) , TyBool)
                                                                                    (TyInt,TyBool) -> ([Expected TyInt  TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2) , TyBool)
                                                       NEq -> case ( snd( checkExp1  ) ,snd( checkExp2  ) )of
                                                                                    (TyBool,TyBool) -> (fst( checkExp1 ) ++ fst( checkExp2), TyBool)
                                                                                    (TyInt,TyInt) -> (fst( checkExp1 ) ++ fst( checkExp2), TyBool)
                                                                                    (TyBool,TyInt) -> ([Expected TyBool  TyInt] ++ fst( checkExp1 ) ++ fst( checkExp2), TyBool)
                                                                                    (TyInt,TyBool) -> ([Expected TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2) , TyBool)



                                                       _ -> case  ( snd( checkExp1  ) ,snd( checkExp2  ) )of
                                                                         (TyBool,TyBool) ->([Expected  TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2)  , TyBool)
                                                                         (TyInt,TyInt) ->  (fst( checkExp1 ) ++ fst( checkExp2) , TyBool)
                                                                         (TyBool,TyInt) -> ( [Expected TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyBool)
                                                                         (TyInt,TyBool) -> ( [Expected TyInt TyBool] ++ fst( checkExp1 ) ++ fst( checkExp2), TyBool)





    where
        checkExp1=checkTypesArgsValidos list expr1 defs
        checkExp2=checkTypesArgsValidos list expr2 defs


checkTypesArgsValidos list (If expr1 expr2 expr3) defs   = case ( snd( checkExp1  ), snd( checkExp2  ) ,snd( checkExp3  ) )of
                                                                      (TyBool,TyBool,TyBool) -> (fst( checkExp1 ) ++fst( checkExp2) ++ fst( checkExp3), TyBool)
                                                                      (TyBool,TyInt,TyInt) -> (fst( checkExp1 ) ++fst( checkExp2) ++ fst( checkExp3), TyInt)
                                                                      (TyBool,TyBool,TyInt) -> ([Expected TyBool  TyInt] ++fst( checkExp1 ) ++fst( checkExp2) ++ fst( checkExp3) , TyBool)
                                                                      (TyBool,TyInt,TyBool) -> ([Expected TyInt  TyBool] ++fst( checkExp1 ) ++fst( checkExp2) ++ fst( checkExp3), TyInt)
                                                                      (TyInt,TyBool,TyBool) -> ([Expected TyBool  TyInt] ++fst( checkExp1 ) ++fst( checkExp2) ++ fst( checkExp3) , TyBool)
                                                                      (TyInt,TyInt,TyInt) ->   ([Expected TyBool   TyInt] ++fst( checkExp1 )  ++fst( checkExp2)  ++ fst( checkExp3)  , TyInt)
                                                                      (TyInt,TyBool,TyInt) -> ([Expected TyBool  TyInt] ++ [Expected TyBool  TyInt] ++fst( checkExp1 ) ++fst( checkExp2) ++ fst( checkExp3) , TyBool)
                                                                      (TyInt,TyInt,TyBool) ->( [Expected TyBool  TyInt] ++ [Expected TyInt TyBool] ++fst( checkExp1 ) ++fst( checkExp2) ++ fst( checkExp3) , TyInt)
    where
        checkExp1=checkTypesArgsValidos list expr1 defs
        checkExp2=checkTypesArgsValidos list expr2 defs
        checkExp3=checkTypesArgsValidos list expr3 defs




checkTypesArgsValidos list (Let typedVar expr1 expr2) defs  | snd(typedVar) == snd(checkExp1) =  (fst( checkExp1 ) ++ fst( checkExp2) , snd checkExp2)
                                                             |otherwise = ([Expected (snd typedVar) (snd checkExp1)] ++ fst( checkExp1 ) ++ fst( checkExp2) , snd checkExp2)

    where
        checkExp1=checkTypesArgsValidos  list expr1 defs -- <- insertar la variable del Let
        checkExp2=checkTypesArgsValidos (insertLista typedVar list) expr2 defs




checkTypesArgsValidos list (App name exprs) defs =  ((checkTypeslenghts (App name exprs) defs)  ++ (fst checkExps) , tipoApp)
     where
         checkExps= checkTypesArgsValidosaux list  (listTiposParam defs name) tipoApp exprs defs
         tipoApp=  typeFromName defs name

listTiposParam :: [FunDef] -> Name -> [Type]
listTiposParam [] name = [] -- Pisadita
listTiposParam (funDefFst:fundfs) name |  (getFuncName funDefFst) == name = getFuncSigTypesdeParams (getFuncParTypes funDefFst)
                                       |  otherwise = listTiposParam fundfs name
-- para q no tiere el error de no exaustivo
-- podria hacer q siga ejecutando recursivo hasta llegar al paso base vacio[]


checkTypesArgsValidosaux :: [(Name, Type)] -> [Type] -> Type -> [Expr] -> Defs -> ([Error], Type)
checkTypesArgsValidosaux _ []  typ _ _= ([],typ)
checkTypesArgsValidosaux _ _  typ [] _= ([],typ)
checkTypesArgsValidosaux list  (arg:args) tipoApp (expr:exprs) defs| snd(checkTypesArgsValidos list expr defs)== arg = ( fst(checkTypesArgsValidos list expr defs) ++ fst(checkTypesArgsValidosaux  list args tipoApp exprs defs) ,tipoApp )
                                                                   | otherwise = ( [Expected arg (snd(checkTypesArgsValidos list expr defs))] ++ fst(checkTypesArgsValidos list expr defs) ++ fst(checkTypesArgsValidosaux  list args tipoApp exprs defs) , tipoApp )
