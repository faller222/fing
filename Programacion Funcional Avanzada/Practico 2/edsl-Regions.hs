{-# Language FlexibleInstances, RecordWildCards #-}

-- | Regions DSL

type Point = (Float,Float)
type Radius = Float

class DSL_Region r where
  inRegion  :: Point -> r -> Bool
  circle    :: Radius -> r
  outside   :: r -> r
  union     :: r -> r -> r
  intersect :: r -> r -> r

aro :: DSL_Region r => Radius -> Radius -> r
aro r1 r2 = outside (circle r1) `intersect` circle r2

-- | Shallow embedding

type SRegion = Point -> Bool

instance DSL_Region SRegion where
  p `inRegion` r   = r p
  circle r         = \p -> magnitude p <= r
  outside r        = \p -> not (r p)
  r `union` r'     = \p -> r p || r' p
  r `intersect` r' = \p -> r p && r' p

magnitude :: Point -> Float
magnitude (x,y) = sqrt (x*x + y*y)

-- | Deep embedding

data DRegion = Circle    Radius
             | Outside   DRegion
             | Union     DRegion DRegion
             | Intersect DRegion DRegion
     deriving Show

instance DSL_Region DRegion where
  circle r         = Circle r
  outside r        = Outside r
  r `union` r'     = Union r r'
  r `intersect` r' = Intersect r r'
  
  p `inRegion` (Circle r)       = magnitude p <= r
  p `inRegion` (Outside r)      = not (p `inRegion` r)
  p `inRegion` (Union r r')     = p `inRegion` r || p `inRegion` r'
  p `inRegion` (Intersect r r') = p `inRegion` r && p `inRegion` r'
