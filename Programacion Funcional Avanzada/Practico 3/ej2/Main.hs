module Main where

import Interp

import System.Environment
import System.Console.GetOpt

opts :: [OptDescr (Maybe String)]
opts = [
  Option ['v'] [] (NoArg $ Nothing) "Imprime el tipo de Expr a evaluar",
  Option ['c'] [] (ReqArg (\s -> Just s) "El argumento es obligatorio") "Determina el archivo con el programa a evaluar"
  ]

-- | Parte D
main = do
          args <- getArgs
          r <- return . foldl f ("", False) . fst' $ getOpt Permute opts args
          if fst r == ""
            then ioError . userError $ "Debe especificar un archivo"
            else return ()
          s <- readFile $ fst r
          exp <- return . fst . head . runP parser $ s
          if snd r
            then print exp
            else return ()
          print $ eval exp
            where
              f (_, b) (Just s) = (s, b)
              f (s, _) Nothing = (s, True)
              fst' (a, _, _) = a
