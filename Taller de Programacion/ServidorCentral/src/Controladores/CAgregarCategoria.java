package Controladores;

import DataTypes.DataCategoria;
import Interfaces.IAgregarCategoria;
import Conceptos.CatCompuesta;
import Conceptos.CatSimple;
import Conceptos.Categoria;
import Manejadores.MCategoria;

public class CAgregarCategoria implements IAgregarCategoria {

    MCategoria mCategoria;

    public CAgregarCategoria() {
        mCategoria = MCategoria.getInstance();

    }

    @Override
    public DataCategoria listarCategorias() {
        return mCategoria.getCategoriasAnidadas().getDataCategoria();
    }

    @Override
    public DataCategoria buscarCategoria(String nCat) {      
        Categoria aux = mCategoria.getCategoria(nCat);
        if (aux==null)
            return null;
        else
            return aux.getDataCategoria();
    }

    @Override
    public void altaCategoria(String Nueva, Boolean esSimple) throws Exception {//Ya Existe Categoria
        altaCategoria(Nueva, esSimple, MCategoria.rootName());//padre es la Raiz
    }

    @Override
    public void altaCategoria(String Nueva, Boolean esSimple, String Padre) throws Exception {//Ya Existe Categoria , Padre no existe o es simple
        Categoria NC;
        if (esSimple) {
            NC = new CatSimple(Nueva);
        } else {
            NC = new CatCompuesta(Nueva);
        }
        mCategoria.addCategoria(NC, Padre);
    }
}
