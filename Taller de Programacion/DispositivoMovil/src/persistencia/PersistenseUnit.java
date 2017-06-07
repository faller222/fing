package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenseUnit {

    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;
    private static PersistenseUnit Instance = null;

    private PersistenseUnit() {
        emf = Persistence.createEntityManagerFactory("DispositivoMovilPU");
        em = emf.createEntityManager();
    }

    public static void dispose() {
        if (em != null) {
            em.close();
            em = null;
        }
        if (emf != null) {
            emf.close();
            emf = null;
        }
        Instance = null;
    }

    public static EntityManager getInstance() {
        if (Instance == null) {
            Instance = new PersistenseUnit();
        }
        return em;
    }
}
