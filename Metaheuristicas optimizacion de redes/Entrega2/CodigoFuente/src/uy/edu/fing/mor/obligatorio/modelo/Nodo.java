package uy.edu.fing.mor.obligatorio.modelo;

import java.util.ArrayList;
import java.util.List;

public final class Nodo {

    private int coordX;
    private int coordY;

    private int nombre;
    private List<Nodo> adyacentes = new ArrayList<>();

    public Nodo(String line) {
        String[] split = line.split("\t");
        this.coordX = Integer.valueOf(split[1]);
        this.coordY = Integer.valueOf(split[2]);
        this.nombre = Integer.valueOf(split[0]);
    }

    public Nodo(int coordX, int coordY, int nombre) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.nombre = nombre;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public int getNombre() {
        return nombre;
    }

    public List<Nodo> getAdyacentes() {
        return adyacentes;
    }

    public void addAdyacente(Nodo ady) {
        adyacentes.add(ady);
    }
}
