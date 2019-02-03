-- | No se me ocurrio como implementar la funcion LookUp, se me dificulta al ver que el tipo a puede ser cualquier cosa
-- | Incluso otro arbol en el cual buscar. El codigo de este archivo es de prueba.

module Ej4 where

data Tree k a = Zero k a
    | Succ (Tree k (Node k a))

data Node k a = Node2 k a k a
    | Node3 k a k a k a


treeEj1 = Zero 2 'z'
treeEj2 = Succ (Zero 2 (Node2 0 'a' 1 'b'))

lookUp :: Eq a => a -> Tree a b -> Maybe b
lookUp k' (Zero k a) = if k'== k then Just a
                                 else Nothing


lookUpNode :: Eq a => a -> Node a b -> Maybe b
lookUpNode k' (Node2 k1 a1 k2 a2) = if k'== k1 then Just a1
                                    else if k'== k2 then Just a2
                                    else Nothing

lookUpNode k' (Node3 k1 a1 k2 a2 k3 a3)= if k'== k1 then Just a1
                                       else if k'== k2 then Just a2
                                       else if k'== k3 then Just a3
                                       else Nothing