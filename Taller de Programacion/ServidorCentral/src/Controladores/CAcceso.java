package Controladores;

import DataTypes.DataAcceso;
import Conceptos.Acceso;
import Interfaces.IAcceso;
import Manejadores.MAcceso;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CAcceso implements IAcceso {

    MAcceso manAc;

    public CAcceso() {
        manAc = MAcceso.getInstance();
    }

    @Override
    public List<DataAcceso> listarAccesos() {
        List<Acceso> listaAc = manAc.getAccesos();
        List<DataAcceso> listaDAc = new ArrayList<DataAcceso>();
        for (Acceso acceso : listaAc) {
            if (acceso != null) {
                listaDAc.add(acceso.getDataAcceso());
            }
        }
        return listaDAc;
    }

    @Override
    public void nuevoAcceso(String user, String ip, String url, String bro, String sis) {
        manAc.agregarAcceso(new Acceso(ip, user, url, bro, sis, new Date()));
    }

    @Override
    public void DatosDePrueba(int MAX) {
        Random rn = new Random();

        for (int i = 0; i < MAX; i++) {
            int Dia = rn.nextInt() % 30;
            int Mes = rn.nextInt() % 11;
            manAc.agregarAcceso(new Acceso("192.168.1.1", "Anonimus", "http://taringa.com", "iExplorer", "iOS", new Date(113, Mes, Dia)));
        }

    }
}
