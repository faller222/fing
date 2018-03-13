package uy.edu.fing.mor.obligatorio.modelo;

import java.util.Collection;
import java.util.HashMap;
import uy.edu.fing.mor.obligatorio.controlador.ProblemaControlador;

public class MatrizAdyacencia {

    private HashMap<Integer, Integer> mAdyId;
    private HashMap<Integer, Integer> mAdy;

    public MatrizAdyacencia(Collection<Arista> aristas) {
        mAdyId = new HashMap<>();
        mAdy = new HashMap<>();
        int i = 0;
        for (Arista a : aristas) {
            i++;
            mAdyId.put(pairGlpkId(a.getIdNodoA(), a.getIdNodoB()), a.getId());
            mAdyId.put(pairGlpkId(a.getIdNodoB(), a.getIdNodoA()), a.getId() + ProblemaControlador.getInstance().cantAristas());

            mAdy.put(pairGlpkId(a.getIdNodoA(), a.getIdNodoB()), i);
            mAdy.put(pairGlpkId(a.getIdNodoB(), a.getIdNodoA()), i + aristas.size());
        }
    }

    public Integer getMatrixAdy(int n1, int n2) {
        final Integer result = mAdy.get(pairGlpkId(n1, n2));
        return result == null ? 0 : result;
    }

    public Integer getAristaId(int n1, int n2) {
        final Integer result = mAdyId.get(pairGlpkId(n1, n2));
        return result == null ? 0 : result;
    }

    private Integer pairGlpkId(int idNodoA, int idNodoB) {
        return idNodoA * ProblemaControlador.getInstance().cantNodos() + idNodoB;
    }
}
