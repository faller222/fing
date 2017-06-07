package Conceptos;

import DataTypes.DataProducto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Producto implements Observable {
    //Atributos

    private String Nombre;
    private String Descripcion;
    private String Especificacion;
    private Integer NRef;
    private float Precio;
    private byte[][] Imgs;
    private Integer CantVentas;
    //Links
    private Proveedor Prove;
    private Map<Integer, Comentario> Coments;//estos son los directos
    private Map<Integer, Comentario> TodosC;//Conoce a todos los comentarios contenidos
    private Map<String, Categoria> Categorias = new HashMap<>();
    private List<Reclamo> Reclamos;
    private Map<String, Puntaje> Puntajes = new HashMap<>(); // Los puntajes con clave el nick del puntuador
    private List<Observer> compradores; //Compradores a notificar
    private Estadistica estadistic;
    //Constructor

    @SuppressWarnings("empty-statement")
    public Producto(DataProducto Data, Proveedor Prove) {
        CantVentas = 0;
        Reclamos = new ArrayList<Reclamo>();
        Coments = new HashMap<Integer, Comentario>();
        TodosC = new HashMap<Integer, Comentario>();
        this.Nombre = Data.getNombre();
        this.Descripcion = Data.getDescripcion();
        this.Especificacion = Data.getEspecificacion();
        this.NRef = Data.getNRef();
        this.Precio = Data.getPrecio();
        this.Imgs = Data.getImgs();
        this.Prove = Prove;
        this.compradores = new ArrayList<Observer>();
        this.estadistic = new Estadistica();
    }

    //Observable
    @Override
    public void addObserver(Observer o) {
        compradores.add(o);
    }

    @Override
    public void delObserver(Observer o) {
        compradores.add(o);
    }

    @Override
    public void delObservers() {
        compradores.clear();
    }

    @Override
    public void notifyObservers() {
        for (Iterator<Observer> it = compradores.iterator(); it.hasNext();) {
            Observer observer = it.next();
            observer.Notificar(this);
        }
    }

    //Getters!
    public String getNombre() {
        return Nombre;
    }

    public Integer getNRef() {
        return NRef;
    }

    public float getPrecio() {
        return Precio;
    }

    public Proveedor getProve() {
        return Prove;
    }

    public Map<String, Categoria> getCategorias() {
        return Categorias;
    }

    //Setters!
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public void setEspecificacion(String Especificacion) {
        this.Especificacion = Especificacion;
    }

    public void setPrecio(float Precio) {
        this.Precio = Precio;
    }

    public void agregarImgs(byte[] Imagen, int Pos) {//debe Agregar 3 si no, no la guarda
        if (Pos < 3 && Pos >= 0) {
            this.Imgs[Pos] = Imagen;
        }
    }

    public void setCategoria(Categoria add) {
        this.Categorias.put(add.getNombre(), add);
    }

    public DataProducto getDataProducto() {
        DataProducto Res = new DataProducto(Nombre, Descripcion, Especificacion, NRef, Precio, Prove.getNickname(), CantVentas);
        Res.agregarImgs(Imgs[0], 0);
        Res.agregarImgs(Imgs[1], 1);
        Res.agregarImgs(Imgs[2], 2);
        for (Map.Entry<Integer, Comentario> entry : Coments.entrySet()) {
            Res.addComen(entry.getValue().getDataComentario());
        }
        Float sumapunt = new Float(0);
        int puntCount[] = new int[5];
        for (int i = 0; i < 5; i++) {
            puntCount[i] = 0;
        }
        for (Map.Entry<String, Puntaje> entry : Puntajes.entrySet()) {
            Puntaje p = entry.getValue();
            Res.addPuntaje(p.getDataPuntaje());
            sumapunt += entry.getValue().getPuntos();
            puntCount[p.getPuntos() - 1]++;
        }
        if (!Puntajes.isEmpty()) {
            sumapunt = sumapunt / Puntajes.size();
        }
        Res.setPromedio(sumapunt);
        Res.setPunt1(puntCount[0]);
        Res.setPunt2(puntCount[1]);
        Res.setPunt3(puntCount[2]);
        Res.setPunt4(puntCount[3]);
        Res.setPunt5(puntCount[4]);
        for (Map.Entry<Integer, Comentario> entry : TodosC.entrySet()) {
            Res.addTodos(entry.getValue().getDataComentario());
        }
        for (Iterator<Reclamo> it = Reclamos.iterator(); it.hasNext();) {
            Reclamo Rec = it.next();
            Res.agregarReclamo(Rec.getDataReclamo());
        }
        Res.setEstadistic(estadistic.getDataEstadistica());
        return Res;
    }

    public void Comentar(Comentario c) {
        Coments.put(c.getId(), c);
        TodosC.put(c.getId(), c);
        notifyObservers();
    }

    public void Responder(Comentario c, Integer Padre) throws Exception {
        Comentario GodFather = TodosC.get(Padre);
        if (GodFather == null) {
            throw new Exception("No existe comentario No." + Padre + " en el Producto " + Nombre);
        }
        GodFather.addRespuesta(c);//se lo agrego al padre
        TodosC.put(c.getId(), c);//y en todaslas del producto
        notifyObservers();
    }

    public void vaciarCategorias() {
        Categorias.clear();
        Categorias = new HashMap<String, Categoria>();
    }

    public void vaciarImgs() {
        Imgs[0] = null;
        Imgs[1] = null;
        Imgs[2] = null;
    }

    public void clearComentarios() {
        for (Map.Entry<Integer, Comentario> entry : Coments.entrySet()) {
            Comentario comentario = entry.getValue();
            comentario.comentsACero();
        }
        for (Map.Entry<Integer, Comentario> entry : TodosC.entrySet()) {
            Comentario comentario = entry.getValue();
            comentario.comentsACero();
        }
        this.Coments.clear();
        this.TodosC.clear();
    }

    public Map<String, Puntaje> getPuntajes() {
        return Puntajes;
    }

    public void agregarPuntaje(String nick, Puntaje pun) {
        this.Puntajes.put(nick, pun);
    }

    public void reclamar(Reclamo rec) {
        Reclamos.add(rec);

    }

    public List<Reclamo> getReclamos() {
        return Reclamos;
    }

    public Estadistica getEstadistic() {
        return estadistic;
    }

    public void vender(int Ventas) {
        CantVentas += Ventas;
    }
}
