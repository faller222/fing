package uy.edu.fing.ae.obligatorio.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uy.edu.fing.ae.obligatorio.modelo.Coord;

public final class Util {

    //Clase con metodos estaticos
    private Util() {
    }

    public static <T> List<T> restaDeConjunto(List<T> grande, List<T> chico) {
        Map<Integer, T> mapa = new HashMap<>();
        grande.forEach((t) -> {
            mapa.put(t.hashCode(), t);
        });

        chico.forEach((t) -> {
            mapa.remove(t.hashCode());
        });
        return new ArrayList<>(mapa.values());
    }

    public static List<Coord> getBound(Coord p, int dist, int maxX, int maxY) {
        List<Coord> result = new ArrayList<>();

        int xMin = p.getCoordX() - dist;
        int xMax = p.getCoordX() + dist;
        int yMin = p.getCoordY() - dist;
        int yMax = p.getCoordY() + dist;

        if (xMin < 0) {
            xMin = 0;
        }
        if (yMin < 0) {
            yMin = 0;
        }
        if (xMax > maxX) {
            xMax = maxX - 1;
        }
        if (yMax > maxY) {
            yMax = maxY - 1;
        }

        for (int i = xMin; i <= xMax; i++) {
            result.add(new Coord(i, yMin));
        }
        for (int i = xMin; i <= xMax; i++) {
            result.add(new Coord(i, yMax));
        }
        for (int i = yMin; i <= yMax; i++) {
            result.add(new Coord(xMin, i));
        }
        for (int i = yMin; i <= yMax; i++) {
            result.add(new Coord(xMax, i));
        }
        return result;
    }

    public static List<Coord> lineBetween(Coord p1, Coord p2) {

        List<Coord> result = new ArrayList<>();
        
        int w = p2.getCoordX() - p1.getCoordX();
        int h = p2.getCoordY() - p1.getCoordY();
        int x = p1.getCoordX();
        int y = p1.getCoordY();
    
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
        if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
        if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
        if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
        int longest = Math.abs(w) ;
        int shortest = Math.abs(h) ;
        if (!(longest>shortest)) {
            longest = Math.abs(h) ;
            shortest = Math.abs(w) ;
            if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
            dx2 = 0 ;            
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {
            result.add(new Coord(x, y));
            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
        return result;
    }
}
