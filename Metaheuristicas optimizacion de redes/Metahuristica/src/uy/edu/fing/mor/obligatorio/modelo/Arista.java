package uy.edu.fing.mor.obligatorio.modelo;

public final class Arista {

    private int id;

    private final int idNodoA;
    private final int idNodoB;

    private Nodo nodoA;
    private Nodo nodoB;

    private final int costo;
    private final int delay;

    public Arista(String line) {
        String[] split = line.split("\t");

        this.idNodoA = Integer.valueOf(split[0]);
        this.idNodoB = Integer.valueOf(split[1]);

        this.costo = Integer.valueOf(split[2]);
        this.delay = Integer.valueOf(split[3]);
    }

    private Arista(int id, int nodoA, int nodoB, int costo, int delay) {
        this.idNodoA = nodoA;
        this.idNodoB = nodoB;

        this.costo = costo;
        this.delay = delay;
    }

    public int getCosto() {
        return costo;
    }

    public int getDelay() {
        return delay;
    }

    public int getIdNodoA() {
        return idNodoA;
    }

    public int getIdNodoB() {
        return idNodoB;
    }

    public Nodo getNodoA() {
        return nodoA;
    }

    public Nodo getNodoB() {
        return nodoB;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNodes(Nodo a, Nodo b) {
        nodoA = a;
        nodoB = b;
    }

    @Override
    public String toString() {
        return "Arista{"
                + "id=" + id
                + ", idNodoA=" + idNodoA
                + ", idNodoB=" + idNodoB
                + ", costo=" + costo
                + ", delay=" + delay + '}';
    }

}
