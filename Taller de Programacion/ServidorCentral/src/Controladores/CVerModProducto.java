package Controladores;

import DataTypes.DataCategoria;
import DataTypes.DataProducto;
import Interfaces.IVerModProducto;
import Conceptos.CatCompuesta;
import Conceptos.CatSimple;
import Conceptos.Categoria;
import Manejadores.MCategoria;
import Manejadores.MProducto;
import Manejadores.MUsuario;
import Conceptos.Producto;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class CVerModProducto implements IVerModProducto {

    private MProducto mProducto;
    private MCategoria mCategoria;
    private MUsuario mUsuario;
    private Categoria Cat;
    private Producto Prod;

    public CVerModProducto() {
        Cat = null;
        Prod = null;
        mProducto = MProducto.getInstance();
        mCategoria = MCategoria.getInstance();
        mUsuario = MUsuario.getInstance();
    }

    @Override
    public DataCategoria listarCategorias() {
        return mCategoria.getCategoriasAnidadas().getDataCategoria();
    }

    @Override
    public DataCategoria seleccionarCategoria(String nCat) throws Exception {//no exsite
        Categoria temp = mCategoria.getCategoria(nCat);
        if (temp == null) {
            throw new Exception("No existe Categoria " + nCat);
        }
        Cat = temp;
        return Cat.getDataCategoria();
    }

    @Override
    public Map<Integer, DataProducto> listarProductosCategoria() throws Exception {// no selecciono categoria o es Compuesta
        if (Cat == null) {
            throw new Exception("No selecciono Categoria");
        }

        if (Cat instanceof CatCompuesta) {
            throw new Exception(Cat.getNombre() + " Es Categoria Compuesta");
        }
        Map<Integer, DataProducto> Retorno = new HashMap<Integer, DataProducto>();
        for (Map.Entry<Integer, Producto> entry : ((CatSimple) Cat).getProductos().entrySet()) {
            Retorno.put(entry.getKey(), entry.getValue().getDataProducto());
        }
        return Retorno;
    }

    @Override
    public DataProducto seleccionarProducto(Integer NRef) throws Exception {// no Existe Producto
        MProducto MP = MProducto.getInstance();
        Prod = MP.getProducto(NRef);
        if (Prod == null) {
            throw new Exception("No existe Producto con No" + NRef + " En el Sistema");
        }
        return Prod.getDataProducto();
    }

    @Override
    public Map<String, DataCategoria> listarCategoriaProductos() throws Exception {// no selecciono Producto
        if (Prod == null) {
            throw new Exception("No selecciono Producto");
        }
        Map<String, DataCategoria> Retorno = new HashMap<String, DataCategoria>();
        for (Map.Entry<String, Categoria> entry : Prod.getCategorias().entrySet()) {
            Retorno.put(entry.getKey(), entry.getValue().getDataCategoria());
        }
        return Retorno;
    }

    @Override
    public void modificarProducto(DataProducto dp) throws Exception { // no selecciono Producto
        if (Prod == null) {
            throw new Exception("No selecciono Producto");
        }
        if (dp.getCategorias().isEmpty()) {
            throw new Exception("Debe Seleccionar almenos una categoria");
        }

        if (dp.getNRef() != Prod.getNRef()) {
            throw new Exception("Error en Seteo de memoria");
        }

        //categorias no ven mas a Producto
        for (Map.Entry<String, Categoria> entry : Prod.getCategorias().entrySet()) {
            Categoria Cate = entry.getValue();
            ((CatSimple) Cate).delProd(Prod);
        }
        //Producto no ve mas a categorias y borra las imagenes
        Prod.vaciarCategorias();
        //Prod.vaciarImgs();

        //ReSeteo el Producto
        Prod.setDescripcion(dp.getDescripcion());
        Prod.setEspecificacion(dp.getEspecificacion());
        Prod.setNombre(dp.getNombre());
        Prod.setPrecio(dp.getPrecio());

        //Vinculo cate-prods
        List<DataCategoria> Cates = dp.getCategorias();
        for (Iterator<DataCategoria> it = Cates.iterator(); it.hasNext();) {
            DataCategoria dC = it.next();
            Categoria Cate = mCategoria.getCategoria(dC.getNombre());
            ((CatSimple) Cate).addProd(Prod);
            Prod.setCategoria(Cate);
        }
        //Añado las nuevas imagenes
//        Prod.agregarImgs(dp.getImgs(0), 0);
//        Prod.agregarImgs(dp.getImgs(1), 1);
//        Prod.agregarImgs(dp.getImgs(2), 2);
    }

    @Override
    public List<DataProducto> buscarProd(String Clave, Integer Pag, int Tipo) {//tipo 0 es como esta, tipo 1 es por Nombre tipo 2 es por Precio
        Map<String, CatSimple> Categs = mCategoria.buscarCategoria(Clave);
        Map<Integer, Producto> Prods = mProducto.buscarProductos(Clave);
        //Junto todo
        Map<Integer, DataProducto> Todo = new HashMap<Integer, DataProducto>();
        for (Map.Entry<Integer, Producto> FP1 : Prods.entrySet()) {//paso todo a data Producto
            Integer key = FP1.getKey();
            Producto Prod = FP1.getValue();
            Todo.put(key, Prod.getDataProducto());
        }
        for (Map.Entry<String, CatSimple> FC : Categs.entrySet()) {//para cada Categoria
            CatSimple Cat = FC.getValue();
            Map<Integer, Producto> ProdsCat = Cat.getProductos();
            for (Map.Entry<Integer, Producto> FP2 : ProdsCat.entrySet()) {//Meto todos los productos
                Integer key = FP2.getKey();
                Producto Prod = FP2.getValue();
                Todo.put(key, Prod.getDataProducto());
            }
        }
        List<DataProducto> lista = new Vector<DataProducto>();
        lista.addAll(Todo.values());

        if (Tipo == 1) {     //Por Nombre
            Collections.sort(lista, NAME_ORDER);
        }
        if (Tipo == 2) {//Por Precio
            Collections.sort(lista);
        }
        if (Tipo == 3) {     //Por Ventas
            Collections.sort(lista, SELL_ORDER);
        }
        int tope = Pag * 8 + 9;
        if (tope > lista.size()) {
            tope = lista.size();
        }

        return lista.subList(Pag * 8, tope);//devuelvo nueve pero mostre ocho, de esta manera sabre si hay siguiente pagina
    }
    static final Comparator<DataProducto> NAME_ORDER =
            new Comparator<DataProducto>() {
        @Override
        public int compare(DataProducto P1, DataProducto P2) {
            String del1 = P1.getNombre().toLowerCase();
            String del2 = P2.getNombre().toLowerCase();

            return del1.compareTo(del2);
        }
    };
    static final Comparator<DataProducto> SELL_ORDER =
            new Comparator<DataProducto>() {
        @Override
        public int compare(DataProducto P1, DataProducto P2) {
            Integer del1 = P1.getCantVentas();
            Integer del2 = P2.getCantVentas();
            return del2 - del1;
        }
    };
}
