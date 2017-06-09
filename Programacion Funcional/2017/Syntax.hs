-- LABORATORIO DE PROGRAMACION FUNCIONAL 2017
-- MODULO DE SINTAXIS

-- Incluye el Abstract Syntax Tree (tipo Program) y
-- una funcion de parsing (parser) que dado un
-- String conteniendo el codigo de un programa
-- si el programa es sintacticamente correcto
-- retorna un Program (o retorna el error).


module Syntax where


import Text.Parsec
import Text.Parsec.Language
import Text.Parsec.String
import Text.Parsec.Expr hiding (Infix)
import Text.Parsec.Token
import qualified Text.Parsec.Expr as P

import Data.List

-- ABSTRACT SYNTAX TREE

data Type = TyInt | TyBool
 deriving Eq


instance Show Type where
 show TyInt  = "integer"
 show TyBool = "boolean"


data Program = Program Defs Expr 

type Name  = String

type Defs = [FunDef]

type TypedVar = (Name, Type)
type TypedFun = (Name, Sig)

data FunDef  = FunDef TypedFun [Name]  Expr

data Sig = Sig [Type] Type
           
data Expr = Var     Name
          | IntLit  Integer
          | BoolLit Bool
          | Infix   Op Expr Expr
          | If      Expr Expr Expr
          | Let     TypedVar Expr Expr
          | App     Name [Expr]

data Op = Add | Sub | Mult | Div
        | Eq | NEq | GTh | LTh  | GEq | LEq
         deriving Eq

type Env    = [TypedVar]


-- PARSER

parser :: String -> Either ParseError Program
parser = parse programParser ""

programParser = do m_whiteSpace
                   defs <- many defparser
                   m_reserved "main"
                   m_reservedOp "="
                   body <- exprparser
                   return (Program defs body)


defparser = do fn <- m_identifier
               m_reservedOp "::"
               dom <- m_parens (m_commaSep typeparser)
               m_reservedOp "->"
               codom <- typeparser
               m_symbol fn
               args <- m_parens (m_commaSep m_identifier)
               m_reservedOp "="
               bdy  <- exprparser 
               return (FunDef (fn, Sig dom codom) args bdy)


typeparser =     (m_reserved "Int" >> return TyInt)
             <|> (m_reserved "Bool" >> return TyBool)
                 


exprparser :: Parser Expr
exprparser = buildExpressionParser table term <?> "expression"

table = [ [P.Infix (m_reservedOp "==" >> return (Infix Eq)) AssocLeft]
        , [P.Infix (m_reservedOp "/=" >> return (Infix NEq)) AssocLeft]
        , [P.Infix (m_reservedOp ">" >> return (Infix GTh)) AssocLeft]
        , [P.Infix (m_reservedOp "<" >> return (Infix LTh)) AssocLeft]
        , [P.Infix (m_reservedOp ">=" >> return (Infix GEq)) AssocLeft]
        , [P.Infix (m_reservedOp "<=" >> return (Infix LEq)) AssocLeft]
        , [P.Infix (m_reservedOp "+" >> return (Infix Add)) AssocLeft]
        , [P.Infix (m_reservedOp "-" >> return (Infix Sub)) AssocLeft]
        , [P.Infix (m_reservedOp "*" >> return (Infix Mult)) AssocLeft]
        , [P.Infix (m_reservedOp "div" >> return (Infix Div)) AssocLeft]
        ]

term = m_parens exprparser
       <|> (do m_reserved "let"
               vn <- m_identifier
               m_reservedOp "::"
               t <- typeparser
               m_reservedOp "="
               e  <- exprparser
               m_reserved "in"
               b  <- exprparser
               return (Let (vn,t) e b))
       <|> (do m_reserved "if"
               cnd <- exprparser
               m_reserved "then"
               e1  <- exprparser
               m_reserved "else"
               e2  <- exprparser
               return (If cnd e1 e2))
       <|> (do vn  <- m_identifier
               option (Var vn) (App vn <$> m_parens (m_commaSep exprparser)))
       <|> (m_reserved "True" >> return (BoolLit True))
       <|> (m_reserved "False" >> return (BoolLit False))
       <|> fmap IntLit m_natural


def :: LanguageDef st
def = emptyDef{ commentStart = "{-"
              , commentEnd = "-}"
              , identStart = letter
              , identLetter = alphaNum
              , opStart = oneOf "/-<>*+=:d"
              , opLetter = oneOf "/-<>*+=:div"
              , reservedOpNames = [ "-", "<", ">","*", "+", "=", "=="
                                  , ">=", "<=", "/=", "::"
                                  ,"div"]
              , reservedNames = ["True", "False"
                                ,"main","if","then","else","let","in"]
              , caseSensitive = True
              }

TokenParser{ parens     = m_parens
           , identifier = m_identifier
           , symbol     = m_symbol
           , reservedOp = m_reservedOp
           , reserved   = m_reserved
           , commaSep   = m_commaSep
           , whiteSpace = m_whiteSpace
           , natural    = m_natural } = makeTokenParser def

