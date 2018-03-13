package uy.edu.fing.mor.obligatorio.controlador;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import uy.edu.fing.mor.obligatorio.modelo.Arista;
import uy.edu.fing.mor.obligatorio.modelo.Nodo;

public class ProblemaControlador {

    private static ProblemaControlador INSTANCIA = null;
    private int height = 0;
    private int width = 0;

    private HashMap<Integer, Nodo> nodos;
    private HashMap<Integer, Arista> aristasId;
    private HashMap<Integer, Arista> aristasPair;

    private ProblemaControlador() {
        nodos = new HashMap<>();
        aristasPair = new HashMap<>();
        aristasId = new HashMap<>();
    }

    public static ProblemaControlador getInstance() {
        if (INSTANCIA == null) {
            INSTANCIA = new ProblemaControlador();
        }
        return INSTANCIA;
    }

    public void addNodo(Nodo n) {
        Integer nombre = n.getNombre();
        if (nodos.containsKey(nombre)) {
            throw new RuntimeException("Nodo ya en la lista: " + nombre);
        }
        if (n.getCoordX() > width) {
            width = n.getCoordX();
        }
        if (n.getCoordY() > height) {
            height = n.getCoordY();
        }
        nodos.put(nombre, n);
    }

    public void addArista(Arista a) {
        Integer id = aristasId.size() + 1;
        a.setId(id);
        Nodo nodoA = nodos.get(a.getIdNodoA());
        Nodo nodoB = nodos.get(a.getIdNodoB());
        nodoA.addAdyacente(nodoB);
        nodoB.addAdyacente(nodoA);
        a.setNodes(nodoA, nodoB);
        aristasId.put(id, a);
        aristasPair.put(pairId(a), a);
    }

    public int cantNodos() {
        return nodos.size();
    }

    public int cantAristas() {
        return aristasId.size();
    }

    public void check() {
        System.out.println("cheking");
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Nodo> getNodos() {
        return (new ArrayList<>(nodos.values()));
    }

    public List<Arista> getAristas() {
        return (new ArrayList<>(aristasId.values()));

    }

    private Integer pairId(Arista a) {
        return pairId(a.getIdNodoA(), a.getIdNodoB());
    }

    private Integer pairId(int idNodoA, int idNodoB) {
        if (idNodoA > idNodoB) {
            return idNodoA * cantNodos() + idNodoB;
        }
        return idNodoB * cantNodos() + idNodoA;
    }

    public Arista getArista(int nodoA, int nodoB) {
        return aristasPair.get(pairId(nodoA, nodoB));
    }

    public Arista getArista(int idArista) {
        return aristasId.get(idArista);
    }

    public Nodo getNodo(int idNodo) {
        return nodos.get(idNodo);
    }

    public void limpiar() {
        nodos = new HashMap<>();
        limpiarAristas();
    }

    public void limpiarAristas() {
        aristasId = new HashMap<>();
        aristasPair = new HashMap<>();
    }
}
