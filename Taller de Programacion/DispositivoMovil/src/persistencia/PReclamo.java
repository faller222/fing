package persistencia;

import conceptos.Reclamo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PReclamo {

    private static EntityManager em;
    private static PReclamo Instance = null;

    private PReclamo() {
    }

    public void init() {
        em.getTransaction().begin();
    }

    public void commit() {
        em.getTransaction().commit();
    }

    public static PReclamo getInstance() {
        if (Instance == null) {
            Instance = new PReclamo();
        }
        em = PersistenseUnit.getInstance();
        return Instance;
    }

    public void saveReclamo(Reclamo nueva) {
        if (!objExists(nueva)) {
            em.persist(nueva);
        }
    }

    public List<dataType.DataReclamo> getReclamos() {
        Query q = em.createQuery("select r from Reclamo r order by r.fecha");

        List<dataType.DataReclamo> res = new ArrayList<>();
        List iter = q.getResultList();
        for (Object O : iter) {
            Reclamo r = (Reclamo) O;
            res.add(r.getDataReclamo());
        }
        return res;
    }

    private boolean objExists(Reclamo rec) {
        String Condicion = "where";
        Condicion += " r.nRef=";
        Condicion += rec.getnRef();
        Condicion += " and r.cliente=\"";
        Condicion += rec.getCliente();
        Condicion += "\" and r.texto=\"";
        Condicion += rec.getTexto() + "\"";

        try {
            Query q = em.createQuery("select r from Reclamo r " + Condicion);
            List result = q.getResultList();
            return result.size() >= 1;
        } catch (Exception e) {
            return false;
        }
    }
}
