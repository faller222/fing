-- LABORATORIO DE PROGRAMACION FUNCIONAL 2017
-- MODULO PRINCIPAL

module Main where

import Syntax
import Checker
import Generator

import System.Environment


main = do args <- getArgs
          compileFile (args !! 0)


compileFile name = do prg <- readFile (name ++ ".mhs")
                      case  compile prg of
                               Right cprg -> writeFile (name ++ ".c") cprg
                               Left  errs -> putStr errs


compile prg = case parser prg of
                   Right ast  -> case checkProgram ast of
                                  Ok        -> Right (genProgram ast)
                                  Wrong err -> Left  (unlines . map show $ err) 

                   Left  cerr -> Left (show cerr)
