package uy.edu.fing.ae.obligatorio.modelo;

import java.util.List;
import uy.edu.fing.ae.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.ae.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.ae.obligatorio.util.PropiedadesEnum;
import uy.edu.fing.ae.obligatorio.util.Random;
import uy.edu.fing.ae.obligatorio.util.Util;

public final class Gen {

    private Coord coord;
    private int angulo;

    public Gen() {
        this.coord = new Coord();
        this.angulo = Random.get(360);
    }

    public Gen(Coord coord, int angulo) {
        this.coord = coord;
        this.angulo = angulo;
    }

    public Gen(int coordX, int coordY) {
        this.coord = new Coord(coordX, coordY);
        this.angulo = Random.get(360);
    }

    public Gen(int coordX, int coordY, int angulo) {
        this.coord = new Coord(coordX, coordY);
        this.angulo = angulo;
    }

    public Gen(Gen copia) {
        this.coord = new Coord(copia.coord);
        this.angulo = copia.angulo;
    }

    public int getAngulo() {
        return angulo;
    }

    public void desvioAngulo(int desvioAngulo) {
        //Esto lo hago asi para asegurarme que quede entre 0 y 360
        this.angulo = (angulo + desvioAngulo + 360) % 360;
    }

    public Coord getCoord() {
        return coord;
    }

    public boolean isValido() {
        ProblemaControlador problema = ProblemaControlador.getInstance();
        int maxX = problema.getWidth();
        int maxY = problema.getHeight();

        if (coord.getCoordX() < 0 || coord.getCoordY() < 0) {
            return false;
        }

        if (coord.getCoordX() >= maxX || coord.getCoordY() >= maxY) {
            return false;
        }

        Interes[][] sala = problema.getSala();
        switch (sala[coord.getCoordY()][coord.getCoordX()]) {
            case NORMAL:
            case MAYOR:
                return true;
        }
        return false;
    }

    public boolean[][] getCubrimiento() {
        
        Integer rango = PropiedadesControlador.getIntProperty(PropiedadesEnum.CAMARA_RANGO);
        Integer amplitud = PropiedadesControlador.getIntProperty(PropiedadesEnum.CAMARA_AMPLITUD);

        ProblemaControlador problema = ProblemaControlador.getInstance();
        Integer rangoCuadrado = rango * rango;

        final int maxX = problema.getWidth();
        final int maxY = problema.getHeight();

        boolean[][] cubrimiento = new boolean[maxY][maxX];

        cubrimiento[coord.getCoordY()][coord.getCoordX()] = true;

        List<Coord> perimetro = Util.getBound(coord, rango, maxX, maxY);

        for (Coord peri : perimetro) {

            int anguloRecta = getAngulo(peri);

            final int angulo360 = anguloRecta + 360;

            if ((anguloRecta >= angulo && anguloRecta <= (angulo + amplitud))
                    || (angulo360 >= angulo && angulo360 <= (angulo + amplitud))) {
                //Calcular todo lo demas

                List<Coord> puntos = Util.lineBetween(coord, peri);

                for (Coord punto : puntos) {

                    int diffX = punto.getCoordX() - coord.getCoordX();
                    int diffY = punto.getCoordY() - coord.getCoordY();

                    int distCuadrada = diffX * diffX;
                    distCuadrada += diffY * diffY;

                    if (distCuadrada <= rangoCuadrado) {
                        Interes interes = problema.getSala(punto.getCoordX(), punto.getCoordY());
                        if (interes == Interes.NUL0) {
                            break;
                        } else {
                            cubrimiento[punto.getCoordY()][punto.getCoordX()] = true;
                        }
                    }
                }
            }
        }
        return cubrimiento;
    }

    private int getAngulo(Coord perimetro) {

        int diffX = perimetro.getCoordX() - coord.getCoordX();
        int diffY = perimetro.getCoordY() - coord.getCoordY();
        int angulo = 0;

        if (diffX == 0) {
            angulo = 90;
            if (diffY < 0) {
                angulo = 270;
            }
        } else {
            double pendiente = (new Double(diffY)) / diffX;
            double arcTan = Math.atan(pendiente);
            angulo = ((Double) Math.toDegrees(arcTan)).intValue();
            if (diffX < 0) {
                angulo += 180;
            } else {
                if (angulo < 0) {
                    angulo += 360;
                }
            }
        }

        return angulo;
    }

    public boolean igualA(Gen other) {
        return coord.igualA(other.getCoord()) && angulo == other.angulo;
    }
}
