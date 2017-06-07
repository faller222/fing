package persistencia;

import conceptos.Reclamo;
import conceptos.ReclamoPendiente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PReclamoPend {

    private static EntityManager em;
    private static PReclamoPend Instance = null;

    private PReclamoPend() {
    }

    public static PReclamoPend getInstance() {
        if (Instance == null) {
            Instance = new PReclamoPend();
        }
        em = PersistenseUnit.getInstance();
        return Instance;
    }

    public void saveReclamo(ReclamoPendiente nueva) {
        em.getTransaction().begin();
        em.persist(nueva);
        em.getTransaction().commit();
    }

    public List<ReclamoPendiente> getReclamos() {
        Query q = em.createQuery("select r from ReclamoPendiente r");
        return q.getResultList();
    }

    public void removeReclamo(ReclamoPendiente viejo) {
        em.getTransaction().begin();
        em.remove(viejo);
        em.getTransaction().commit();
    }
}
