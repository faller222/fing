package uy.edu.fing.mor.obligatorio.genetic;

import com.sun.istack.internal.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.GlpkException;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_iocp;
import org.gnu.glpk.glp_prob;
import org.gnu.glpk.glp_smcp;
import uy.edu.fing.mor.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.mor.obligatorio.modelo.Arista;
import uy.edu.fing.mor.obligatorio.modelo.Linea;
import uy.edu.fing.mor.obligatorio.modelo.MatrizAdyacencia;
import uy.edu.fing.mor.obligatorio.modelo.Nodo;
import uy.edu.fing.mor.obligatorio.util.PropiedadesEnum;

public class Parte2 {

    //Clase metodos estaticos
    private Parte2() {
    }

    public static List<Arista> run() {

        ProblemaControlador problema = ProblemaControlador.getInstance();

        List<Arista> aristas = problema.getAristas();
        String[] especialesStr = PropiedadesControlador.getProperty(PropiedadesEnum.ESPECIALES).split(";");
        Integer[] especiales = Arrays.asList(especialesStr).stream().map(Integer::valueOf).toArray(size -> new Integer[size]);

        return run(aristas, especiales, new ArrayList<>());
    }

    public static List<Arista> run(List<Arista> aristas, Integer[] lineasPorFuentes, @NotNull List<Linea> prohibidas) {
        final Integer cantCentros = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_SUMIDEROS);
        final Integer cantFuentes = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_FUENTES);

        final ProblemaControlador problema = ProblemaControlador.getInstance();

        List<Nodo> nodos = problema.getNodos();

        final Integer cantNodos = nodos.size();
        final Integer cantAristas = aristas.size();
        final Integer cantVariables = cantAristas * (cantFuentes * 2 + 1);
        final Integer offset = cantAristas * 2;

        //Estructura auxiliar
        MatrizAdyacencia mAdy = new MatrizAdyacencia(aristas);

        // Create problem
        glp_prob lp = GLPK.glp_create_prob();

        try {
            GLPK.glp_set_prob_name(lp, "Problema");

            // Define columns
            GLPK.glp_add_cols(lp, cantVariables);
            for (int i = 0; i < cantFuentes; i++) {
                for (Arista arista : aristas) {
                    int i1 = offset * i + mAdy.getMatrixAdy(arista.getIdNodoA(), arista.getIdNodoB());
                    int i2 = offset * i + mAdy.getMatrixAdy(arista.getIdNodoB(), arista.getIdNodoA());

                    GLPK.glp_set_col_name(lp, i1, "a" + i1);
                    GLPK.glp_set_col_kind(lp, i1, GLPKConstants.GLP_BV);
                    GLPK.glp_set_col_bnds(lp, i1, GLPKConstants.GLP_DB, 0, 1);

                    GLPK.glp_set_col_name(lp, i2, "a" + i2);
                    GLPK.glp_set_col_kind(lp, i2, GLPKConstants.GLP_BV);
                    GLPK.glp_set_col_bnds(lp, i2, GLPKConstants.GLP_DB, 0, 1);
                }
            }

            for (Arista arista : aristas) {
                int i1 = offset * cantFuentes + mAdy.getMatrixAdy(arista.getIdNodoA(), arista.getIdNodoB());

                GLPK.glp_set_col_name(lp, i1, "a" + i1);
                GLPK.glp_set_col_kind(lp, i1, GLPKConstants.GLP_BV);
                GLPK.glp_set_col_bnds(lp, i1, GLPKConstants.GLP_DB, 0, 1);
            }

            // Create constraints
            // Allocate memory
            SWIGTYPE_p_int ind = GLPK.new_intArray(cantVariables);
            SWIGTYPE_p_double val = GLPK.new_doubleArray(cantVariables);

            // Create rows
            GLPK.glp_add_rows(lp, cantFuentes * cantNodos + cantFuentes * 2 * cantAristas + prohibidas.size());

            // Set row details
            //int idNodoEcuacion = 0;
            for (int c = 0; c < cantFuentes; c++) {
                int offsetN = 2 * cantAristas * c;

                for (Nodo nodoRep : nodos) {
                    int nodo = nodoRep.getNombre();
                    //Para la parte 2 ignoro los primeros nodos
                    if (nodo <= cantCentros) {
                        continue;
                    }

                    int idNodoEcuacion = 0;
                    for (int n = 1; n <= cantNodos; n++) {

                        Integer n1 = mAdy.getMatrixAdy(n, nodo);
                        Integer n2 = mAdy.getMatrixAdy(nodo, n);
                        if (n1 != 0) {
                            //flujo entrante
                            idNodoEcuacion++;
                            GLPK.intArray_setitem(ind, idNodoEcuacion, offsetN + n1);
                            GLPK.doubleArray_setitem(val, idNodoEcuacion, -1.);
                            //menos flujo saliente
                            idNodoEcuacion++;
                            GLPK.intArray_setitem(ind, idNodoEcuacion, offsetN + n2);
                            GLPK.doubleArray_setitem(val, idNodoEcuacion, 1.);
                        }
                    }
                    if (idNodoEcuacion > 0) {
                        final int id = (cantNodos * c) + nodo;
                        GLPK.glp_set_row_name(lp, id, "n_" + id);
                        GLPK.glp_set_mat_row(lp, id, idNodoEcuacion, ind, val);
                        // balance de flujo para fuentes
                        if ((c + cantCentros + 1) == nodo) {
                            final Integer lineasPorFuente = lineasPorFuentes[nodo - 1];
                            GLPK.glp_set_row_bnds(lp, id, GLPKConstants.GLP_FX, lineasPorFuente, lineasPorFuente);
                        } else {
                            GLPK.glp_set_row_bnds(lp, id, GLPKConstants.GLP_FX, 0, 0);
                        }
                    }

                }
            }

            int iterEq = cantNodos * cantFuentes;
            for (int arista = 0; arista < cantAristas; arista++) {
                int offsetFinal = cantFuentes * 2 * cantAristas + arista + 1;

                for (int c = 0; c < (cantFuentes * 2); c++) {
                    iterEq++;
                    int offsetA = cantAristas * c + arista + 1;

                    GLPK.intArray_setitem(ind, 1, offsetA);
                    GLPK.doubleArray_setitem(val, 1, 1.);

                    GLPK.intArray_setitem(ind, 2, offsetFinal);
                    GLPK.doubleArray_setitem(val, 2, -1.);

                    GLPK.glp_set_mat_row(lp, iterEq, 2, ind, val);

                    GLPK.glp_set_row_bnds(lp, iterEq, GLPKConstants.GLP_UP, 0, 0);
                }

            }

            //este pedazo es solo para el calculo de las cotas
            for (Linea prohibida : prohibidas) {
                int idTramoEcuacion = 0;
                int offsetFinal = cantFuentes * 2 * cantAristas;

                iterEq++;
                for (Arista tramo : prohibida.getTramos()) {
                    idTramoEcuacion++;
                    GLPK.intArray_setitem(ind, idTramoEcuacion, offsetFinal + tramo.getId());
                    GLPK.doubleArray_setitem(val, idTramoEcuacion, 1.);
                }

                GLPK.glp_set_row_name(lp, iterEq, "P_" + iterEq);
                GLPK.glp_set_mat_row(lp, iterEq, idTramoEcuacion, ind, val);
                GLPK.glp_set_row_bnds(lp, iterEq, GLPKConstants.GLP_DB, 0, idTramoEcuacion - 1);

            }

            // Free memory
            GLPK.delete_intArray(ind);
            GLPK.delete_doubleArray(val);

            // Define objective
            GLPK.glp_set_obj_name(lp, "costo");
            GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MIN);

            int z = offset * cantFuentes;
            GLPK.glp_set_obj_coef(lp, 0, 0);

            for (Arista arista : aristas) {
                z++;
                GLPK.glp_set_obj_coef(lp, z, arista.getCosto());
            }

            //GLPK.glp_write_lp(lp, null, "parte2.lp");
            // https://stackoverflow.com/questions/47092912/glpk-for-java-binary-variable-mip-gives-fractional-result
            // Solve model
            glp_smcp smcpParm = new glp_smcp();
            GLPK.glp_init_smcp(smcpParm);
            GLPK.glp_simplex(lp, smcpParm);

            // Solve model
            glp_iocp iocpParm = new glp_iocp();
            iocpParm.setPresolve(GLPK.GLP_ON);
            GLPK.glp_init_iocp(iocpParm);
            int ret = GLPK.glp_intopt(lp, iocpParm);

            // Retrieve solution
            if (ret == 0) {
                return get_lp_solution(lp);
            } else {
                System.out.println("The problem could not be solved");
                return new ArrayList<>();
            }

        } catch (GlpkException ex) {
            throw new RuntimeException(ex);
        } finally {
            // Free memory
            GLPK.glp_delete_prob(lp);
        }
    }

    static List<Arista> get_lp_solution(glp_prob lp) {
        final int cantAristas = ProblemaControlador.getInstance().cantAristas();
        final Integer cantFuentes = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_FUENTES);
        List<Arista> aristas = new ArrayList<>();

        int i;
        String name;
        Double val;

        int n = GLPK.glp_get_num_cols(lp);
        int p = (cantAristas * 2 * cantFuentes + 1);
        for (i = p; i <= n; i++) {
            name = GLPK.glp_get_col_name(lp, i);
            val = GLPK.glp_mip_col_val(lp, i);
            int valI = val.intValue();

            if (valI != 0) {
                if (valI == 1) {
                    int id = getIdByName(name);
                    Arista arista = ProblemaControlador.getInstance().getArista(id);
                    aristas.add(arista);
                } else {
                    throw new RuntimeException("name: " + name + " - value: " + val);
                }
            }
        }
        return aristas;
    }

    private static int getIdByName(String name) {
        final int mod = ProblemaControlador.getInstance().cantAristas();
        final String idStr = name.substring(1);
        final Integer id = Integer.valueOf(idStr);
        final Integer result = id % mod;
        if (result == 0) {
            return mod;
        } else {
            return result;
        }
    }
}
