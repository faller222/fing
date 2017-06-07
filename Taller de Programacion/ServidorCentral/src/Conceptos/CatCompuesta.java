package Conceptos;

import DataTypes.DataCategoria;
import java.util.HashMap;
import java.util.Map;

public class CatCompuesta extends Categoria {

    private Map<String, Categoria> CateHijas = new HashMap<String, Categoria>();

    public CatCompuesta(String Nombre) {
        this.Nombre = Nombre;
    }

    public void addHija(Categoria Hija) {
        CateHijas.put(Hija.getNombre(), Hija);
    }

    @Override
    public DataCategoria getDataCategoria() {
        DataCategoria Resultado = new DataCategoria(Nombre, false);

        for (Map.Entry<String, Categoria> entry : CateHijas.entrySet()) {//Recursion!!!
            Resultado.addHija(entry.getValue().getDataCategoria());

        }
        return Resultado;
    }
}
