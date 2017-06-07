package persistencia;

import conceptos.OrdenCompra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class POrdenCompra {

    private static EntityManager em;
    private static POrdenCompra Instance = null;

    private POrdenCompra() {
    }

    public void init() {
        em.getTransaction().begin();
    }

    public void commit() {
        em.getTransaction().commit();
    }

    public static POrdenCompra getInstance() {
        if (Instance == null) {
            Instance = new POrdenCompra();
        }
        em = PersistenseUnit.getInstance();
        return Instance;
    }

    public void saveOrden(OrdenCompra nueva) {
        if (!objExists(nueva.getnOrd())) {
            em.persist(nueva);
        } else {
            em.merge(nueva);
        }
    }

    public List<dataType.DataOrdenCompra> getOrdenes() {
        Query q = em.createQuery("select c from OrdenCompra c order by c.nOrd");

        List<dataType.DataOrdenCompra> res = new ArrayList<>();
        List iter = q.getResultList();
        for (Object O : iter) {
            OrdenCompra oC = (OrdenCompra) O;
            res.add(oC.getDataOrden());
        }
        return res;
    }

    private boolean objExists(int idOrden) {
        Query q = em.createQuery("select c from OrdenCompra c where c.nOrd=" + idOrden);
        return q.getResultList().size() == 1;
    }
}
