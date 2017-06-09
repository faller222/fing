-- LABORATORIO DE PROGRAMACION FUNCIONAL 2016
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
import Text.Parsec.Expr
import Text.Parsec.Token


-- ABSTRACT SYNTAX TREE

data Program = Program Name Defs Body 
 deriving Show

type Name  = String

type Defs = [VarDef]


data VarDef  = VarDef Name Type
 deriving Show


data Type = TyInt | TyBool | TyArray Integer Integer Type 
 deriving Eq


instance Show Type where
 show TyInt  = "integer"
 show TyBool = "boolean"
 show (TyArray ini fin ty) = "array [" ++ show ini ++ 
                             " .. " ++ show fin ++ "] of " ++
                             show ty 

type Body = [Stmt]

data Stmt = Asig  Name [Expr] Expr
          | If    Expr Body Body
          | For   Name Expr Expr Body
          | While Expr Body
          | Write Expr
          | Read  Name
  deriving Show

data Expr = Var     Name [Expr]
          | IntLit  Integer
          | BoolLit Bool
          | Unary   UOp Expr
          | Binary  BOp Expr Expr
 deriving (Eq,Show)

data UOp = Not | Neg
 deriving (Eq,Show)

data BOp = Or | And | Equ | Less 
         | Plus | Minus | Mult | Div | Mod
 deriving (Eq,Show)


type Env    = [(Name,Type)]


-- PARSER

parser :: String -> Either ParseError Program
parser = parse programParser ""

programParser = do m_whiteSpace
                   m_reserved "program"
                   name <- m_identifier
                   m_semi  
                   defs <- defsparser 
                   body <- bodyparser
                   m_reservedOp "."
                   return (Program name defs body)



defsparser =  do m_reserved "var" 
                 vars <- many varparser
                 return vars 

varparser =   do v <- m_identifier
                 m_reservedOp ":" 
                 t <- typeparser
                 m_semi
                 return (VarDef v t) 

typeparser =     (m_reserved "integer" >> return TyInt)
             <|> (m_reserved "boolean" >> return TyBool)
             <|> do m_reserved "array"
                    m_reservedOp "[" 
                    ini <- m_natural
                    m_reservedOp ".."
                    fin <- m_natural
                    m_reservedOp "]"
                    m_reserved "of"
                    ty <- typeparser
                    return (TyArray ini fin ty) 
                 


bodyparser :: Parser Body
bodyparser =  do m_reserved "begin" 
                 stmts <- m_semiSep stmtparser
                 m_reserved "end"
                 return stmts 

stmtparser :: Parser Stmt
stmtparser =      do  v  <- m_identifier
                      is <- many indexparser 
                      m_reservedOp ":="
                      e <- exprparser
                      return (Asig v is e)
                     
              <|> do  m_reserved "if"
                      b <- exprparser
                      m_reserved "then"
                      ps <- bodyparser
                      m_reserved "else"
                      qs <- bodyparser
                      return (If b ps qs)

              <|> do  m_reserved "for"
                      i <- m_identifier
                      m_reservedOp ":="
                      ini <- exprparser
                      m_reserved "to"
                      fin <- exprparser
                      m_reserved "do"
                      ps <- bodyparser
                      return (For i ini fin ps)

              <|> do  m_reserved "while"
                      b <- exprparser
                      m_reserved "do"
                      ps <- bodyparser
                      return (While b ps)

              <|> do  m_reserved "writeln"
                      e <- m_parens exprparser
                      return (Write e)
              <|> do  m_reserved "readln"
                      e <- m_parens m_identifier
                      return (Read e)


exprparser :: Parser Expr
exprparser = buildExpressionParser table term <?> "expression"
table = [ [Prefix (m_reservedOp "not" >> return (Unary Not))]
        , [Prefix (m_reservedOp "-" >> return (Unary Neg))]
        , [Infix (m_reservedOp "or" >> return (Binary Or)) AssocLeft]
        , [Infix (m_reservedOp "and" >> return (Binary And)) AssocLeft]
        , [Infix (m_reservedOp "=" >> return (Binary Equ)) AssocLeft]
        , [Infix (m_reservedOp "<" >> return (Binary Less)) AssocLeft]
        , [Infix (m_reservedOp "+" >> return (Binary Plus)) AssocLeft]
        , [Infix (m_reservedOp "-" >> return (Binary Minus)) AssocLeft]
        , [Infix (m_reservedOp "*" >> return (Binary Mult)) AssocLeft]
        , [Infix (m_reservedOp "div" >> return (Binary Div)) AssocLeft]
        , [Infix (m_reservedOp "mod" >> return (Binary Mod)) AssocLeft]
        ]

term = m_parens exprparser
       <|> (do vn  <- m_identifier
               is  <- many indexparser 
               return (Var vn is))
       <|> (m_reserved "true" >> return (BoolLit True))
       <|> (m_reserved "false" >> return (BoolLit False))
       <|> fmap IntLit m_natural

indexparser = do i <- m_brackets exprparser
                 return i

def :: LanguageDef st
def = emptyDef{ commentStart = "(*"
              , commentEnd = "*)"
              , identStart = letter
              , identLetter = alphaNum
              , opStart = oneOf "-<*+=:aondm[]."
              , opLetter = oneOf "-<*+=:andortivm[]."
              , reservedOpNames = [ "-", "<", "*", "+", "=", ":=",":"
                                  , "[","]",".."
                                  , "and", "or", "not","div","mod"]
              , reservedNames = ["true", "false"
                                , "program","var","procedure"
                                , "array","of", "integer", "boolean"
                                , "begin","end"
                                , "if", "then", "else"
                                , "while", "do"
                                , "for" , "to"
                                , "writeln", "readln"]
              , caseSensitive = False
              }

TokenParser{ parens     = m_parens
           , brackets   = m_brackets
           , semi       = m_semi
           , identifier = m_identifier
           , reservedOp = m_reservedOp
           , reserved   = m_reserved
           , semiSep    = m_semiSep
           , semiSep1   = m_semiSep1
           , whiteSpace = m_whiteSpace
           , natural    = m_natural } = makeTokenParser def
