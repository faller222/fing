package Interfaces;

import DataTypes.DataAcceso;
import java.util.List;

public interface IAcceso {

    public List<DataAcceso> listarAccesos();

    public void nuevoAcceso(String user, String ip, String url, String bro, String sis);

    public void DatosDePrueba(int MAX);
}
