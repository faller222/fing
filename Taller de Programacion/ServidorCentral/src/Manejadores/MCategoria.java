package Manejadores;

import Conceptos.CatCompuesta;
import Conceptos.CatSimple;
import Conceptos.Categoria;
import java.util.HashMap;
import java.util.Map;

public class MCategoria { //Singleton

    private static String rootName = "Categorias";
    private static MCategoria instancia = null;
    private Map<String, Categoria> Categorias = new HashMap<String, Categoria>();
    private CatCompuesta raiz;

    private MCategoria() {
        this.raiz = new CatCompuesta(rootName);
        Categorias.put(raiz.getNombre(), raiz);
    }

    public static MCategoria getInstance() {
        if (instancia == null) {
            instancia = new MCategoria();
        }
        return instancia;
    }

    public static String rootName() {
        return rootName;
    }

    public Categoria getCategoria(String Nombre) {
        return Categorias.get(Nombre);
    }

    public synchronized void addCategoria(Categoria nueva, String Padre) throws Exception {
        if (!disponibleCategoria(nueva.getNombre())) {
            throw new Exception("Ya existe Categoria :" + nueva.getNombre() + " En el Sistema");
        }
        Categoria Pad = Categorias.get(Padre);
        if (Pad == null) {
            throw new Exception("No existe Categoria " + Padre + " En el Sistema");
        }
        if (Pad instanceof CatSimple) {
            throw new Exception(Padre + " es Categoria Simple");
        }
        Categorias.put(nueva.getNombre(), nueva);
        CatCompuesta Padr = (CatCompuesta) Pad;
        Padr.addHija(nueva);
    }

    public Categoria getCategoriasAnidadas() {
        return raiz;
    }

    public void clearCategorias() {
        this.Categorias.clear();
        //necesito que la raiz se mantenga!!!
        this.raiz = new CatCompuesta(rootName);
        Categorias.put(raiz.getNombre(), raiz);
    }

    public boolean disponibleCategoria(String nombre) {
        return !(Categorias.containsKey(nombre));
    }

    public Map<String, CatSimple> buscarCategoria(CharSequence nom) {
        Map<String, CatSimple> result = new HashMap<String, CatSimple>();
        for (Map.Entry<String, Categoria> entry : Categorias.entrySet()) {
            String string = entry.getKey();
            Categoria cat = entry.getValue();
            if (string.contains(nom) && (cat instanceof CatSimple)) {
                result.put(string, (CatSimple) cat);
            }
        }
        return result;
    }
}
