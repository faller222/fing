package Extras;

import Adapter.IConfirmarOrden;
import Adapter.ISesion;
import Extras.Utilidad;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import publisher.DataCliente;
import publisher.DataEstado;
import publisher.DataLinea;
import publisher.DataOrdenCompra;
import publisher.DataUsuario;

public class pdfCreator extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static ByteArrayOutputStream createPdf(DataOrdenCompra DOC) {
        //Nuevo Documento
        Document documento = new Document();

        //Creo el archivo
//        FileOutputStream ficheroPdf;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //ficheroPdf = new FileOutputStream("/ens/home01/tprog077/NetBeansProjects/lib/hola.pdf");
            PdfWriter.getInstance(
                    documento,
                    baos).setInitialLeading(20);
//            PdfWriter.getInstance(
//                    documento,
//                    ficheroPdf).setInitialLeading(20);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //Codigo para escribir el PDF

        try {
            documento.open();
            //Inicio Contenido...

            //Parrafo Simple
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph(" "));
            //Parrafo Formateado
//            Paragraph parrafo2 = new Paragraph("nuestro segundo Texto");
//            parrafo2.setAlignment(1);//el 1 es para centrar
//            documento.add(parrafo2);
            //Creando Tabla
            PdfPTable tabla = new PdfPTable(4);

            Paragraph P = new Paragraph("Contenido:");
            PdfPCell celda = new PdfPCell(P);
            celda.setBorder(Rectangle.BOTTOM);
            celda.setColspan(4);
            //cantidad de columnas que ocupa esta celda
            celda.setRowspan(1);
            //cantidad de filas que ocupa esta celda

            tabla.addCell(celda);
            celda = (new PdfPCell(new Paragraph("Cantidad:")));
            celda.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(celda);
            celda = (new PdfPCell(new Paragraph("Producto:")));
            celda.setBorder(Rectangle.NO_BORDER);
            tabla.addCell(celda);
            celda = (new PdfPCell(new Paragraph("Precio:")));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(2);
            tabla.addCell(celda);
            celda = (new PdfPCell(new Paragraph("SubTotal:")));
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(2);
            tabla.addCell(celda);

            for (DataLinea dl : DOC.getLineas()) {
                celda = (new PdfPCell(new Paragraph(dl.getCantidad().toString())));
                celda.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celda);
                celda = (new PdfPCell(new Paragraph(dl.getProd().getNombre())));
                celda.setBorder(Rectangle.NO_BORDER);
                tabla.addCell(celda);
                celda = (new PdfPCell(new Paragraph("U$S " + dl.getPrecioProd())));
                celda.setBorder(Rectangle.NO_BORDER);
                celda.setHorizontalAlignment(2);
                tabla.addCell(celda);
                celda = (new PdfPCell(new Paragraph("U$S " + dl.getPrecioLinea())));
                celda.setBorder(Rectangle.NO_BORDER);
                celda.setHorizontalAlignment(2);
                tabla.addCell(celda);
            }
            //Dato total
            P = new Paragraph("Total: U$S " + DOC.getTotal());
            celda = new PdfPCell(P);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(4);
            celda.setHorizontalAlignment(2);
            tabla.addCell(celda);
            //Datos Evolucion
            P = new Paragraph(" ");
            celda = new PdfPCell(P);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(4);
            tabla.addCell(celda);
            P = new Paragraph("Evolucion:");
            celda = new PdfPCell(P);
            celda.setBorder(Rectangle.BOTTOM);
            celda.setColspan(4);
            tabla.addCell(celda);
            for (DataEstado de : DOC.getEstados()) {
                celda = (new PdfPCell(new Paragraph(de.getEst().name())));
                celda.setBorder(Rectangle.NO_BORDER);
                celda.setColspan(2);
                tabla.addCell(celda);
                celda = (new PdfPCell(new Paragraph(Utilidad.formatS(de.getFecha()))));
                celda.setBorder(Rectangle.NO_BORDER);
                celda.setColspan(2);
                tabla.addCell(celda);
            }

            //ImagenQR
            String Dominio = Utilidad.getDominio();
            Dominio += "UnaOrden?Orden=" + DOC.getNumero();


            BarcodeQRCode qr = new BarcodeQRCode(Dominio, 200, 200, null);
            Image qrImage = qr.getImage();

            qrImage.setAbsolutePosition(500f, 650f);

            celda = new PdfPCell(qrImage);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setColspan(4);
            celda.setRowspan(4);
            tabla.addCell(celda);

            documento.add(tabla);

            //Fin Contenido...
            documento.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return baos;
    }

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Integer Num = Integer.parseInt(request.getParameter("N"));
        IConfirmarOrden ICO = new IConfirmarOrden();

        HttpSession Sesion = request.getSession();
        ISesion IS = (ISesion) Sesion.getAttribute("Sesion");
        TipoLogIn TL = (TipoLogIn) Sesion.getAttribute("TipoLog");

        try {
            boolean Correcto = false;
            if (TL == TipoLogIn.CLIENTE) {
                DataUsuario user = IS.verInfoPerfil();
                DataCliente clie = (DataCliente) user;
                for (Integer it : clie.getOrdenes()) {
                    Correcto = Correcto || (it == Num);
                }
            } else {
                throw new Exception("Debes ser Cliente Logueado. ");
            }

            if (!Correcto) {
                throw new Exception("No te corresponde esta orden.");
            }

            DataOrdenCompra DOC = ICO.seleccionarOrden(Num);
            ByteArrayOutputStream baos = createPdf(DOC);
            /* TODO output your page here. You may use following sample code. */
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");

            response.setContentType("application/pdf");

            response.setContentLength(baos.size());

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();
        } catch (Exception E) {
            response.sendRedirect("/");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
