package Conceptos;

import DataTypes.DataProveedor;
import DataTypes.DataUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Proveedor extends Usuario implements Observable {

    private String compania;
    private String sitioWeb;
    private Map<String, Producto> productos;
    private List<Observer> compradores;

    public Proveedor(DataUsuario dataU) throws Exception {
        super(dataU); //llamo al constructor de Usuario
        DataProveedor dP = (DataProveedor) dataU;
        this.setCompania(dP.getCompania());
        this.setSitioWeb(dP.getSitioWeb());
        this.productos = new HashMap();
        this.compradores = new ArrayList<Observer>();
    }

    @Override
    public void addObserver(Observer o) {
        compradores.add(o);
    }

    @Override
    public void delObserver(Observer o) {
        compradores.remove(o);
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

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void addProducto(Producto prod) {
        notifyObservers();
        this.productos.put(prod.getNombre(), prod);
    }

    public void removeProducto(Producto prod) {
        this.productos.remove(prod.getNombre());
    }

    public Map<String, Producto> getProductos() {
        return this.productos;
    }

    @Override
    public DataUsuario getDataUsuario() {
        DataProveedor dp = new DataProveedor(this.getNickname(),
                this.getPass(),
                this.getMail(),
                this.getNombre(),
                this.getApellido(),
                this.getFechaNac(),
                this.sitioWeb,
                this.compania,
                this.getImagen());
        ArrayList<Integer> lista = new ArrayList<Integer>();
        for (Map.Entry<String, Producto> entry : productos.entrySet()) {
            lista.add(entry.getValue().getNRef());
        }
        dp.setProductos(lista);
        dp.setOnline(Online);
        return dp;
    }
}
