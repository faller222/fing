/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DataTypes.DataCliente;
import DataTypes.DataProducto;
import DataTypes.DataProveedor;
import DataTypes.DataReclamo;
import Interfaces.IReclamo;
import Conceptos.Cliente;
import Manejadores.MProducto;
import Manejadores.MUsuario;
import Conceptos.Producto;
import Conceptos.Proveedor;
import Conceptos.Reclamo;
import Conceptos.Usuario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tprog076
 */
public class CReclamo implements IReclamo {

    private MUsuario mUsuario;
    private MProducto mProducto;
    private Usuario User;
    private Producto Prod;

    public CReclamo() {
        mUsuario = MUsuario.getInstance();
        mProducto = MProducto.getInstance();
    }

    @Override
    public DataCliente seleccionarCliente(String nick) throws Exception {//Usuario no existe,No es Proveedor
        Usuario Temp = mUsuario.getUsuario(nick);
        if (Temp == null) {
            throw new Exception("No existe Usuario " + nick + " En el Sistema");
        }
        if (Temp instanceof Proveedor) {
            throw new Exception(nick + " Es un Proveedor");
        }
        User = Temp;
        return (DataCliente) Temp.getDataUsuario();
    }

    @Override
    public DataProducto seleccionarProducto(Integer NRef) throws Exception {// no Existe Producto

        if (User == null) {
            throw new Exception("No Ha Seleccionado Usuario");
        }
        Cliente clie = (Cliente) User;
        Map<Integer, Producto> prods = clie.obtenerProductos(); //obtengo productos comprados
        Prod = prods.get(NRef);
        if (Prod == null) {
            throw new Exception("El Producto con No" + NRef + "no se ha comprado");
        }
        return Prod.getDataProducto();
    }

    @Override
    public void altaReclamo(String texto) {
        Reclamo recl = new Reclamo(new Date(), texto, (Cliente) User, Prod.getNRef());
        Prod.reclamar(recl);
    }

    public void altaReclamo(String reclamo, String respuesta, Date F) {
        Reclamo recl = new Reclamo(F, reclamo, (Cliente) User, Prod.getNRef());
        recl.setRespuesta(respuesta);
        Prod.reclamar(recl);
    }

    @Override
    public DataProveedor seleccionarProveedor(String nick) throws Exception {//Usuario no existe,No es Proveedor
        Usuario Temp = mUsuario.getUsuario(nick);
        if (Temp == null) {
            throw new Exception("No existe Usuario " + nick + " En el Sistema");
        }
        if (Temp instanceof Cliente) {
            throw new Exception(nick + " Es un Cliente");
        }
        User = Temp;
        return (DataProveedor) Temp.getDataUsuario();
    }

    @Override
    public List<DataReclamo> listarReclamos() {
        Proveedor Prov = (Proveedor) User;
        Map<String, Producto> prods = Prov.getProductos();
        List<DataReclamo> recs = new ArrayList(); //Reclamos para devolver
        for (Map.Entry<String, Producto> entry : prods.entrySet()) {
            String name = entry.getKey();
            Producto producto = entry.getValue();
            List<Reclamo> reclamos = producto.getReclamos();
            for (Iterator<Reclamo> it = reclamos.iterator(); it.hasNext();) {
                Reclamo reclamo = it.next();
                DataReclamo drec = reclamo.getDataReclamo();
                drec.setNomProd(name);
                drec.setnRef(producto.getNRef());
                recs.add(drec);
            }
        }
        Collections.sort(recs, DATE_ORDER);
        return recs;
    }
    static final Comparator<DataReclamo> DATE_ORDER =
            new Comparator<DataReclamo>() {
        @Override
        public int compare(DataReclamo R1, DataReclamo R2) {
            return R2.getFecha().compareTo(R1.getFecha());
        }
    };

    @Override
    public void respRec(String txt, Integer nRec, Integer nRef) {
        MProducto mp = MProducto.getInstance();
        Producto p = mp.getProducto(nRef);

        for (Reclamo rec : p.getReclamos()) {
            if (rec.getId() == nRec) {
                rec.setRespuesta(txt);
            }
        }
    }
}
