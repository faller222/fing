package uy.edu.fing.ae.obligatorio.modelo;

import uy.edu.fing.ae.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.ae.obligatorio.util.Random;

public final class Coord {

    private int coordX;
    private int coordY;

    public Coord() {
        int maxX = ProblemaControlador.getInstance().getWidth();
        int maxY = ProblemaControlador.getInstance().getHeight();

        coordX = Random.get(maxX);
        coordY = Random.get(maxY);
    }

    public Coord(Coord copy) {
        this.coordX = copy.coordX;
        this.coordY = copy.coordY;
    }

    public Coord(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public int getCoordX() {
        return coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public boolean igualA(Coord other) {
        return coordX == other.coordX && coordY == other.coordY;
    }

    @Override
    public String toString() {
        return "Coord{" + "coordX=" + coordX + ", coordY=" + coordY + '}';
    }

}
