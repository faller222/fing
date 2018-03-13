package uy.edu.fing.ae.obligatorio.controlador;

import java.awt.Color;
import java.awt.image.BufferedImage;
import uy.edu.fing.ae.obligatorio.modelo.Interes;
import uy.edu.fing.ae.obligatorio.util.Logger;

public class ProblemaControlador {

    private static ProblemaControlador INSTANCIA = null;
    private int height = 0;
    private int width = 0;
    private int cantValidos = 0;

    private Interes[][] sala;

    private ProblemaControlador() {
    }

    public static synchronized ProblemaControlador getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new ProblemaControlador();
        }
        return INSTANCIA;
    }

    public void check() {
        Logger.debug("cheking");
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Interes[][] getSala() {
        return sala;
    }

    public Interes getSala(int cordX, int cordY) {
        return sala[cordY][cordX];
    }

    public int getCantValidos() {
        return cantValidos;
    }

    public void limpiar() {
        height = 0;
        width = 0;
        sala = null;
    }

    public void cargar(BufferedImage image) {
        cantValidos = 0;
        width = image.getWidth();
        height = image.getHeight();

        sala = new Interes[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                try {
                    Color color = new Color(image.getRGB(x, y));
                    sala[y][x] = makeInteres(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
                } catch (Exception e) {
                    throw new RuntimeException("(" + x + "," + y + ")", e);
                }
            }
        }
    }

    private Interes makeInteres(int alpha, int red, int green, int blue) {
        if (blue < 50 && green  < 50 && red  < 50) {
            return Interes.NUL0;
        }
        if (blue > 250 && green > 250 && red > 250) {
            cantValidos++;
            return Interes.NORMAL;
        }

        if (blue < 50 && green < 50 && red > 200) {
            cantValidos++;
            return Interes.MAYOR;
        }
        throw new RuntimeException("Color no interpretado: R: " + red + " G: " + green + " B: " + blue + " alpha: " + alpha);
    }
}
