package Conceptos;

import DataTypes.DataCategoria;

public abstract class Categoria {

    protected String Nombre;

    public String getNombre() {
        return Nombre;
    }

    public abstract DataCategoria getDataCategoria();
}
