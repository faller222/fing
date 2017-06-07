package conexion;

import persistencia.POrdenCompra;
import persistencia.PReclamo;
import adapter.AReclamo;
import adapter.AVerOrden;
import conceptos.OrdenCompra;
import conceptos.Reclamo;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.WebServiceException;
import publisher.DataOrdenCompra;
import publisher.DataReclamo;
import static extras.utilDate.toDate;

public class Conexion {

    public Conexion() {
    }

    public List<dataType.DataOrdenCompra> getOrdenes() throws Exception {
        List<dataType.DataOrdenCompra> res;
        POrdenCompra POC = POrdenCompra.getInstance();
        try {
            AVerOrden AVO = new AVerOrden();
            AVO.seleccionarCliente(CSesion.getInstance().verInfoPerfil().getNickname());
            List<publisher.DataOrdenCompra> Datas = AVO.listarOrdenesCliente();
            res = new ArrayList<>();
            POC.init();
            for (DataOrdenCompra DOC : Datas) {
                OrdenCompra oC = new OrdenCompra(DOC);
                res.add(oC.getDataOrden());
                POC.saveOrden(oC);
            }
            POC.commit();
        } catch (WebServiceException e) {
            // res = new ArrayList<>();
            res = POC.getOrdenes();
        }

        return res;
    }

    public List<dataType.DataReclamo> getReclamos() throws Exception {
        List<dataType.DataReclamo> res;
        PReclamo CRE = PReclamo.getInstance();
        try {
            AReclamo ARE = new AReclamo();
            ARE.seleccionarProveedor(CSesion.getInstance().verInfoPerfil().getNickname());
            List<publisher.DataReclamo> Datas = ARE.listarReclamos();
            res = new ArrayList<>();
            CRE.init();
            for (DataReclamo dR : Datas) {
                Reclamo r = new Reclamo();
                r.setCliente(dR.getClient());
                r.setFecha(toDate(dR.getFecha()));
                r.setNomProd(dR.getNomProd());
                r.setTexto(dR.getTexto());
                r.setnRef(dR.getNRef());
                res.add(r.getDataReclamo());
                CRE.saveReclamo(r);
            }
            CRE.commit();
        } catch (WebServiceException e) {
            res = CRE.getReclamos();
        }
        return res;
    }
}
