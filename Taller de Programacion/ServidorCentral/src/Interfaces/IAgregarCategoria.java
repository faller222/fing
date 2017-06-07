package Interfaces;

import DataTypes.DataCategoria;

public interface IAgregarCategoria {

    public DataCategoria listarCategorias();

    public DataCategoria buscarCategoria(String nCat);

    public void altaCategoria(String Nueva, Boolean esSimple) throws Exception;//Ya Existe Categoria

    public void altaCategoria(String Nueva, Boolean esSimple, String Padre) throws Exception;//Ya Existe Categoria
}
