{-# LANGUAGE FlexibleContexts, TypeSynonymInstances,
FlexibleInstances, MultiParamTypeClasses, InstanceSigs #-}
module Ejercicio3 where

import Control.Monad (liftM,ap)
import Control.Monad.Trans
import Control.Monad.Reader
import Control.Monad.Except
import Control.Monad.Identity

data Expr = Let String Expr Expr
          | Add Expr Expr
          | Div Expr Expr
          | Num Int
          | Var String
    deriving Show

-- | Parte A

data Error = ErrorDivZero | ErrorUnbound String deriving Show
type Env = [(String,Int)]

interp :: (MonadReader Env m, MonadError Error m) => Expr -> m Int
interp (Let s e1 e2) = do
                          e <- ask
                          v <- interp e1
                          local (\_ -> concat [[(s,v)],e]) (interp e2)
interp (Num n)          = return n
interp (Var s)          = do
                          e <- ask
                          case lookup s e of
                              Nothing -> throwError $ ErrorUnbound s
                              Just a -> return a
interp (Add e1 e2)   = do
                          v1 <- interp e1
                          v2 <- interp e2
                          return (v1 + v2)
interp (Div e1 e2)   = do
                          v1 <- interp e1
                          v2 <- interp e2
                          if v2 == 0
                            then throwError ErrorDivZero
                            else return (v1 `div` v2)

-- | Parte B
-- ExceptT Error (ReaderT Env Identity) Int
evalE :: Expr -> Either Error Int
evalE exp = runIdentity $ runReaderT (runExceptT $ interp exp) []

-- | Parte C

data Res a = DivZero
          | Unbound String
          | Res a
          deriving Show
  
newtype ResT m a = ResT {runResT :: m (Res a)}

instance Functor Res where
  fmap = liftM

instance Applicative Res where
  pure = return
  (<*>) = ap

instance Monad m => Functor (ResT m) where
  fmap = liftM

instance Monad m => Applicative (ResT m) where
  pure = return
  (<*>) = ap

instance Monad Res where
  return a = Res a
  DivZero >>= _ = DivZero
  Unbound s >>= _ = Unbound s
  Res a >>= f = f a

instance Monad m => Monad (ResT m) where
  return a = ResT $ return $ return a
  x >>= f = ResT $ do
    x' <- runResT x
    getMRes x'
      where
        getMRes DivZero = return DivZero
        getMRes (Unbound s) = return $ Unbound s
        getMRes (Res a) = runResT $ f a

instance MonadReader Env m => MonadReader Env (ResT m) where
  ask = ResT $ do
    e <- ask
    return $ return e
  local f (ResT m) = ResT $ local f m

instance Monad m => MonadError Error (ResT m) where
  throwError ErrorDivZero = ResT $ return DivZero
  throwError (ErrorUnbound s) = ResT $ return $ Unbound s
  (ResT m) `catchError` f = ResT $ do
     x <- m
     getMRes x
       where
         getMRes DivZero = runResT $ f ErrorDivZero
         getMRes (Unbound s) = runResT $ f (ErrorUnbound s)
         getMRes x@(Res a) = return x

-- | Parte D

evalR :: Expr -> Res Int
evalR exp = runIdentity $ runReaderT (runResT $ interp' exp) [] where
  interp' :: Expr -> ResT (ReaderT Env Identity) Int
  interp' = interp
