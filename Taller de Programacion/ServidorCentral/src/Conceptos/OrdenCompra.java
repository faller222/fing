package Conceptos;

import DataTypes.DataEstado;
import DataTypes.DataLinea;
import DataTypes.DataOrdenCompra;
import Extras.TipoEstado;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.FundingConstraint;
import com.paypal.svcs.types.ap.FundingTypeInfo;
import com.paypal.svcs.types.ap.FundingTypeList;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.common.ClientDetailsType;
import com.paypal.svcs.types.common.RequestEnvelope;
import Extras.Configuration;
import com.paypal.exception.ClientActionRequiredException;
import com.paypal.exception.HttpErrorException;
import com.paypal.exception.InvalidCredentialException;
import com.paypal.exception.InvalidResponseDataException;
import com.paypal.exception.MissingCredentialException;
import com.paypal.exception.SSLConfigurationException;
import com.paypal.sdk.exceptions.OAuthException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OrdenCompra implements Observable {

    private Integer Numero;
    private List<Linea> Lineas = new ArrayList<Linea>();
    private ArrayList<DataEstado> Estados = new ArrayList<DataEstado>();
    private Observer Obs;

    @Override
    public void addObserver(Observer o) {
        Obs = o;
    }

    @Override
    public void delObserver(Observer o) {
        Obs = null;
    }

    @Override
    public void delObservers() {
        Obs = null;
    }

    @Override
    public void notifyObservers() {
        if (Obs != null) {
            Obs.Notificar((Observable) this);
        }
    }

    public OrdenCompra() {
        addEstado(TipoEstado.Recibida, new Date());
    }

    public List<Linea> getLineas() {
        return Lineas;
    }

    public OrdenCompra(Date F) {
        addEstado(TipoEstado.Recibida, F);
    }

    public void addLinea(Linea Nueva) {
        Lineas.add(Nueva);
    }

    public void setNumero(Integer Numero) {
        this.Numero = Numero;
    }

    public Integer getNumero() {
        return Numero;
    }

    public Linea getLineaPorProducto(int num) {
        Linea Ret = null;
        for (Iterator<Linea> it = Lineas.iterator(); it.hasNext();) {
            Linea linea = it.next();
            if (linea.getNumProd() == num) {
                Ret = linea;
            }
        }
        return Ret;
    }

    public DataOrdenCompra getDataOrdenCompra() {
        ArrayList<DataLinea> lain = new ArrayList();
        for (Iterator<Linea> it = Lineas.iterator(); it.hasNext();) {
            Linea linea = it.next();
            lain.add(linea.getDataLinea());
        }
        return new DataOrdenCompra(Numero, lain, Estados, getTotal());
    }

    public boolean contieneProducto(int Num) {
        boolean ret = false;
        for (Iterator<Linea> it = Lineas.iterator(); it.hasNext();) {
            Linea linea = it.next();
            ret = ret || (linea.getNumProd() == Num);
        }
        return ret;
    }

    public Map<Integer, Producto> getProductosOrden() {
        Map<Integer, Producto> prods = new HashMap<>();
        for (Iterator<Linea> it = Lineas.iterator(); it.hasNext();) {
            Linea linea = it.next();
            prods.put(linea.getNumProd(), linea.getProd());
        }
        return prods;
    }

    public List<DataEstado> getEstados() {
        return Estados;
    }

    public DataEstado getEstado() {
        return Estados.get(Estados.size() - 1);
    }

    public void addEstado(DataEstado est) {
        this.Estados.add(est);
    }

    public void addEstado(TipoEstado TE, Date F) {
        DataEstado est = new DataEstado();
        est.setFecha(F);
        est.setEst(TE);
        Estados.add(est);
    }

    public Float getTotal() {
        float ax = 0;
        for (Linea linea : Lineas) {
            ax += linea.getSubTotal();
        }
        return ax;
    }

    private double getTotal2() {
        double ax = 0;
        for (Linea linea : Lineas) {
            ax += linea.getSubTotal();
        }
        ax = ax * 10;
        int pre = (int) ax;
        return ((double) pre) / 10;
    }

    public void confirmar(Date F) throws Exception {
        if (getEstado().getEst() != TipoEstado.Preparada) {
            throw new Exception("Orden NO Preparada");
        }
        addEstado(TipoEstado.Confirmada, F);
        for (Linea linea : Lineas) {
            linea.getProd().vender(linea.getCantidad());
        }
        this.notifyObservers();
    }

    public void cancelar(Date F) throws Exception {
        if (getEstado().getEst() != TipoEstado.Recibida) {
            throw new Exception("Orden NO Recibida");
        }
        RequestEnvelope requestEnvelope = new RequestEnvelope("en_US");

        PayRequest req = new PayRequest();

        List<Receiver> receiver = new ArrayList<Receiver>();

        Receiver rec = new Receiver();
        double precio = getTotal2();
        rec.setAmount(getTotal2());
        rec.setEmail("client.directmarket@gmail.com");
        receiver.add(rec);
        ReceiverList receiverlst = new ReceiverList(receiver);
        req.setReceiverList(receiverlst);
        req.setRequestEnvelope(requestEnvelope);
        ClientDetailsType clientDetails = new ClientDetailsType();
        clientDetails.setPartnerName("DirectMarket");
        req.setClientDetails(clientDetails);
        req.setSenderEmail("directmarket16-facilitator@gmail.com");
        FundingConstraint fundingConstraint = new FundingConstraint();
        List<FundingTypeInfo> fundingTypeInfoList = new ArrayList<FundingTypeInfo>();
        FundingTypeInfo fundingTypeInfo = new FundingTypeInfo("BALANCE");
        fundingTypeInfoList.add(fundingTypeInfo);
        FundingTypeList fundingTypeList = new FundingTypeList(
                fundingTypeInfoList);
        fundingConstraint.setAllowedFundingType(fundingTypeList);
        req.setFundingConstraint(fundingConstraint);
        req.setActionType("PAY");
        req.setCancelUrl("https://pcunix148:8443/DirectMarket");
        req.setCurrencyCode("USD");
        req.setReturnUrl("https://pcunix148:8443/DirectMarket");
        Map<String, String> configurationMap = Configuration.getAcctAndConfig();
        configurationMap.put("http.UseProxy", "true");
        configurationMap.put("http.ProxyPort", "3128");
        configurationMap.put("http.ProxyHost", "proxy.fing.edu.uy");
        configurationMap.put("https.UseProxy", "true");
        configurationMap.put("https.ProxyPort", "3128");
        configurationMap.put("https.ProxyHost", "proxy.fing.edu.uy");
        AdaptivePaymentsService service = new AdaptivePaymentsService(configurationMap);

        try {
            PayResponse resp = service.pay(req);
//           if (resp != null) {
//                if (resp.getResponseEnvelope().getAck().toString()
//                        .equalsIgnoreCase("SUCCESS")) {
//                    Map<Object, Object> map = new LinkedHashMap<Object, Object>();
//                    map.put("Ack", resp.getResponseEnvelope().getAck());
//
//                    /**
//                     * Correlation identifier. It is a 13-character,
//                     * alphanumeric string (for example, db87c705a910e) that is
//                     * used only by PayPal Merchant Technical Support. Note: You
//                     * must log and store this data for every response you
//                     * receive. PayPal Technical Support uses the information to
//                     * assist with reported issues.
//                     */
//                    map.put("Correlation ID", resp.getResponseEnvelope().getCorrelationId());
//
//                    /**
//                     * Date on which the response was sent, for example:
//                     * 2012-04-02T22:33:35.774-07:00 Note: You must log and
//                     * store this data for every response you receive. PayPal
//                     * Technical Support uses the information to assist with
//                     * reported issues.
//                     */
//                    map.put("Time Stamp", resp.getResponseEnvelope().getTimestamp());
//
//                    /**
//                     * The pay key, which is a token you use in other Adaptive
//                     * Payment APIs (such as the Refund Method) to identify this
//                     * payment. The pay key is valid for 3 hours; the payment
//                     * must be approved while the pay key is valid.
//                     */
//                    map.put("Pay Key", resp.getPayKey());
//
//                    /**
//                     * The status of the payment. Possible values are: CREATED �
//                     * The payment request was received; funds will be
//                     * transferred once the payment is approved COMPLETED � The
//                     * payment was successful INCOMPLETE � Some transfers
//                     * succeeded and some failed for a parallel payment or, for
//                     * a delayed chained payment, secondary receivers have not
//                     * been paid ERROR � The payment failed and all attempted
//                     * transfers failed or all completed transfers were
//                     * successfully reversed REVERSALERROR � One or more
//                     * transfers failed when attempting to reverse a payment
//                     * PROCESSING � The payment is in progress PENDING � The
//                     * payment is awaiting processing
//                     */
//                    map.put("Payment Execution Status", resp.getPaymentExecStatus());
//                    if (resp.getDefaultFundingPlan() != null) {
//                        /**
//                         * Default funding plan.
//                         */
//                        map.put("Default Funding Plan", resp.getDefaultFundingPlan().getFundingPlanId());
//                    }
//
//                    map.put("Redirect URL",
//                            "<a href=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-payment&paykey="
//                            + resp.getPayKey()
//                            + ">https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_ap-payment&paykey="
//                            + resp.getPayKey() + "</a>");
//                    session.setAttribute("map", map);
//                    response.sendRedirect("Response.jsp");
//                } else {
//                    session.setAttribute("Error", resp.getError());
//                    response.sendRedirect("Error.jsp");
//                }
//            }
        } catch (SSLConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidCredentialException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (HttpErrorException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidResponseDataException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientActionRequiredException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingCredentialException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OAuthException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        addEstado(TipoEstado.Cancelada, F);
        this.notifyObservers();
    }

    public void preparar(Date F) throws Exception {
        if (getEstado().getEst() != TipoEstado.Recibida) {
            throw new Exception("Orden NO Recibida");
        }
        addEstado(TipoEstado.Preparada, F);
        this.notifyObservers();
    }
}
