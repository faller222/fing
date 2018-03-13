package uy.edu.fing.mor.obligatorio.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.gnu.glpk.GLPK;
import org.gnu.glpk.GLPKConstants;
import org.gnu.glpk.GlpkException;
import org.gnu.glpk.SWIGTYPE_p_double;
import org.gnu.glpk.SWIGTYPE_p_int;
import org.gnu.glpk.glp_prob;
import org.gnu.glpk.glp_smcp;
import uy.edu.fing.mor.obligatorio.controlador.ProblemaControlador;
import uy.edu.fing.mor.obligatorio.controlador.PropiedadesControlador;
import uy.edu.fing.mor.obligatorio.modelo.Arista;
import uy.edu.fing.mor.obligatorio.modelo.MatrizAdyacencia;
import uy.edu.fing.mor.obligatorio.modelo.Nodo;

public class GlpkUtil {

    //Clase metodos estaticos
    private GlpkUtil() {
    }

    public static List<Arista> run(List<Arista> aristas, Integer[] lineasPorFuentes) {
        return run(aristas, lineasPorFuentes, 0, true);
    }

    public static List<Arista> costo(List<Arista> aristas, Integer[] lineasPorFuentes, int ruido) {
        return run(aristas, lineasPorFuentes, ruido, true);
    }

    public static List<Arista> delay(List<Arista> aristas, Integer[] lineasPorFuentes, int ruido) {
        return run(aristas, lineasPorFuentes, ruido, false);
    }

    private static List<Arista> run(List<Arista> aristas, Integer[] lineasPorFuentes, int ruido, boolean costos) {
        if (ruido < 0 || ruido > 100) {
            throw new RuntimeException("El ruido debe comprender un valor entre 0 y 100");
        }

        final ProblemaControlador problema = ProblemaControlador.getInstance();

        List<Nodo> nodos = problema.getNodos();

        Integer cantNodos = nodos.size();

        Integer cantAristas = aristas.size();
        Integer cantVariables = 2 * cantAristas;

        Integer cantCentros = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_SUMIDEROS);
        Integer cantFuentes = PropiedadesControlador.getIntProperty(PropiedadesEnum.CANT_FUENTES);

        //Estructura auxiliar
        MatrizAdyacencia mAdy = new MatrizAdyacencia(aristas);

        // Create problem
        glp_prob lp = GLPK.glp_create_prob();

        try {
            GLPK.glp_set_prob_name(lp, "Problema");

            // Define columns
            GLPK.glp_add_cols(lp, cantVariables);

            for (Arista arista : aristas) {
                int i1 = mAdy.getMatrixAdy(arista.getIdNodoA(), arista.getIdNodoB());
                int i2 = mAdy.getMatrixAdy(arista.getIdNodoB(), arista.getIdNodoA());

                String n1 = "a" + mAdy.getAristaId(arista.getIdNodoA(), arista.getIdNodoB());
                String n2 = "a" + mAdy.getAristaId(arista.getIdNodoB(), arista.getIdNodoA());

                GLPK.glp_set_col_name(lp, i1, n1);
                GLPK.glp_set_col_kind(lp, i1, GLPKConstants.GLP_BV);
                GLPK.glp_set_col_bnds(lp, i1, GLPKConstants.GLP_DB, 0, 1);

                GLPK.glp_set_col_name(lp, i2, n2);
                GLPK.glp_set_col_kind(lp, i2, GLPKConstants.GLP_BV);
                GLPK.glp_set_col_bnds(lp, i2, GLPKConstants.GLP_DB, 0, 1);
            }

            // Create constraints
            // Allocate memory
            SWIGTYPE_p_int ind = GLPK.new_intArray(cantVariables);
            SWIGTYPE_p_double val = GLPK.new_doubleArray(cantVariables);

            // Create rows
            GLPK.glp_add_rows(lp, cantNodos);

            // Set row details
            int j = 0;
            for (Nodo nodo : nodos) {
                j++;
                //Para la parte 1 ignoro los primeros nodos
                if (j <= cantCentros) {
                    continue;
                }

                int k = 0;
                for (int i = 1; i <= cantNodos; i++) {

                    Integer i1 = mAdy.getMatrixAdy(i, nodo.getNombre());
                    Integer i2 = mAdy.getMatrixAdy(nodo.getNombre(), i);
                    if (i1 != 0) {
                        k++;
                        GLPK.intArray_setitem(ind, k, i1);
                        GLPK.doubleArray_setitem(val, k, 1.);

                        k++;
                        GLPK.intArray_setitem(ind, k, i2);
                        GLPK.doubleArray_setitem(val, k, -1.);
                    }
                }
                if (k > 0) {
                    GLPK.glp_set_row_name(lp, j, "n" + j);
                    if (j <= (cantCentros + cantFuentes)) {
                        //Si es un nodo Fuente
                        final Integer lineasPorFuente = lineasPorFuentes[j - 1];
                        GLPK.glp_set_row_bnds(lp, j, GLPKConstants.GLP_FX, lineasPorFuente, lineasPorFuente);
                    } else {
                        GLPK.glp_set_row_bnds(lp, j, GLPKConstants.GLP_FX, 0, 0);
                    }
                    GLPK.glp_set_mat_row(lp, j, k, ind, val);
                }
            }

            // Free memory
            GLPK.delete_intArray(ind);
            GLPK.delete_doubleArray(val);

            // Define objective
            GLPK.glp_set_obj_name(lp, "costo");
            GLPK.glp_set_obj_dir(lp, GLPKConstants.GLP_MIN);

            Integer seed = PropiedadesControlador.getIntProperty(PropiedadesEnum.SEED);
            Random r = (seed == null ? new Random() : new Random(seed));

            int z = 0;
            GLPK.glp_set_obj_coef(lp, 0, 0);
            for (Arista arista : aristas) {
                int factor = (ruido == 0 ? 0 : r.nextInt(2 * ruido) - ruido) + 100;
                int objetivo = (costos ? arista.getCosto() : arista.getDelay());
                int costoAlterado = (factor * objetivo) / 100;
                z++;
                GLPK.glp_set_obj_coef(lp, z, costoAlterado);
            }
            for (Arista arista : aristas) {
                int factor = (ruido == 0 ? 0 : r.nextInt(2 * ruido) - ruido) + 100;
                int objetivo = (costos ? arista.getCosto() : arista.getDelay());
                int costoAlterado = (factor * objetivo) / 100;
                z++;
                GLPK.glp_set_obj_coef(lp, z, costoAlterado);
            }

            //GLPK.glp_write_lp(lp, null, "test.lp");
            // Solve model
            glp_smcp parm = new glp_smcp();
            GLPK.glp_init_smcp(parm);
            int ret = GLPK.glp_simplex(lp, parm);

            // Retrieve solution
            if (ret == 0) {
                return get_lp_solution(lp);
            } else {
                System.out.println("The problem could not be solved");
                return new ArrayList<>();
            }

        } catch (GlpkException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            // Free memory
            GLPK.glp_delete_prob(lp);
        }
    }

    static List<Arista> get_lp_solution(glp_prob lp) {
        List<Arista> aristas = new ArrayList<>();

        int i;
        String name;
        double val;

        name = GLPK.glp_get_obj_name(lp);
        val = GLPK.glp_get_obj_val(lp);
        //System.out.print(name);
        //System.out.print(" = ");
        //System.out.println(val);

        int n = GLPK.glp_get_num_cols(lp);
        for (i = 1; i <= n; i++) {
            name = GLPK.glp_get_col_name(lp, i);
            val = GLPK.glp_get_col_prim(lp, i);
            if (val > 0) {
                int id = getIdByName(name);
                Arista arista = ProblemaControlador.getInstance().getArista(id);
                aristas.add(arista);
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
