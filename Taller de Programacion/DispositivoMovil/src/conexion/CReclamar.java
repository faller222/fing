package conexion;

import adapter.AReclamo;
import conceptos.ReclamoPendiente;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import persistencia.PReclamoPend;

public class CReclamar {

    private static CReclamar Instance = null;
    private List<ReclamoPendiente> Pendientes;

    private CReclamar() {
        Pendientes = new ArrayList<>();
        Vincular();
    }

    public static CReclamar getInstance() {
        if (Instance == null) {
            Instance = new CReclamar();
        }
        return Instance;
    }

    public void reclamar(String Nick, String Reclamo, Integer nref) {
        ReclamoPendiente rec = new ReclamoPendiente();
        rec.setClie(Nick);
        rec.setTexto(Reclamo);
        rec.setnRef(nref);
        try {
            AReclamo IR = new AReclamo();
            IR.seleccionarCliente(Nick);
            IR.seleccionarProducto(nref);
            IR.altaReclamo(Reclamo);
        } catch (Exception e) {
            PReclamoPend.getInstance().saveReclamo(rec);
        }
    }

    public void reclamar(ReclamoPendiente rec) throws Exception {
        AReclamo IR = new AReclamo();
        IR.seleccionarCliente(rec.getClie());
        IR.seleccionarProducto(rec.getnRef());
        IR.altaReclamo(rec.getTexto());
    }

    private synchronized void intento() {
        int i = 0;

        while (i < Pendientes.size()) {
            ReclamoPendiente rec = Pendientes.get(i);
            try {
                reclamar(rec);
                PReclamoPend.getInstance().removeReclamo(rec);
                i++;
            } catch (Exception e) {
            }
        }
        Pendientes = PReclamoPend.getInstance().getReclamos();
    }

    private void Vincular() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                intento();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }
}
