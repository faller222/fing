package presentacion;

import adapter.AVerProd;
import conexion.CSesion;
import extras.utilImage;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import publisher.DataCategoria;
import publisher.DataProducto;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.ws.WebServiceException;
import static presentacion.Principal.setPanel;
import publisher.DataComentario;
import publisher.DataProveedor;
import publisher.DataReclamo;
import publisher.DataUsuario;

public class VerProd extends javax.swing.JPanel {

    private static Integer nRef = null;
    public static javax.swing.JPanel Padre = null;
    public static DataProducto dProd = null;
    Integer foto = 1;
    List<DataReclamo> reclamosCli = new ArrayList<>();
    private static List<DataCategoria> cates;
    Integer iRec = 1;
    Integer maxRec = 0;

    public static void setnRef(Integer nRef) throws Exception {
        VerProd.nRef = nRef;

        DataProducto dP = null;
        try {
            AVerProd AVP = new AVerProd();
            dP = AVP.seleccionarProducto(nRef);
            cates = AVP.listarCategoriaProductos();

        } catch (WebServiceException WSE) {
            throw new Exception("Sin Conexion.");
        } catch (Exception e) {
            throw new Exception("No se Puede Mostrar Producto.");
        }

        dProd = dP;

    }

    public VerProd() throws Exception {
        if (dProd == null) {
            throw new Exception("Debe Indicar un Producto.");
        }
        Padre.setVisible(false);

        initComponents();

        txtNRef.setText(dProd.getNRef().toString());
        txtProve.setText(dProd.getProveedor());
        txtPrecio.setText(dProd.getPrecio().toString());
        txtEsp.setText(dProd.getEspecificacion());
        txtDesc.setText(dProd.getDescripcion());
        txtNom.setText(dProd.getNombre());

        if (dProd.getImg0() != null) {
            try {
                BufferedImage BI = utilImage.toBufferedImage(dProd.getImg0());
                ImageIcon II = utilImage.fixImagen(BI, 100, 100);
                Img.setIcon(II);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        Img.setText("");

        DefaultListModel LModel = new DefaultListModel();

        for (DataCategoria dC : cates) {
            LModel.addElement(dC.getNombre());
        }
        listCate.setModel(LModel);

        DataUsuario dU = CSesion.getInstance().verInfoPerfil();
        if (dU == null) {
            tabbed.remove(recPane);
            btnChat.setVisible(false);
        } else {
            if (dU instanceof DataProveedor) {
                tabbed.remove(recPane);
                btnChat.setVisible(false);
            } else {

                List<DataReclamo> reclamos = dProd.getReclamos();
                boolean puedeReclamar = CSesion.getInstance().puedeComentar(nRef);
                if (!puedeReclamar) {
                    tabbed.remove(recPane);
                } else {
                    for (DataReclamo rec : reclamos) {
                        if (rec.getClient().equals(dU.getNickname())) {
                            reclamosCli.add(rec);
                        }
                    }
                    maxRec = reclamosCli.size();
                    iRec = maxRec - 1;
                    if (reclamosCli.isEmpty()) {
                        subPaneRec.setVisible(false);
                    } else {
                        DataReclamo r = reclamosCli.get(iRec);
                        txtRec.setText(r.getTexto());
                        txtResp.setText(r.getRespuesta());
                    }
                    Integer num = (maxRec - iRec);
                    lblNum.setText(num.toString() + "/" + (maxRec).toString());
                }

            }
        }

        cargarCome(comeTree, dProd);
        Dimension d = new Dimension(320, 1000);
        jScrollPane3.setSize(d);
        tabbed.setSize(d);
        infoPane.setSize(d);
        recPane.setSize(d);
        setVisible(true);
    }

    private DefaultMutableTreeNode crearModeloTree(DataComentario come) {
        DefaultMutableTreeNode Nombre = new DefaultMutableTreeNode(come.getClie());
        DefaultMutableTreeNode Come = new DefaultMutableTreeNode(come.getTexto());
        Nombre.add(Come);
        List<DataComentario> resp = come.getRespuestas();
        for (DataComentario resu : resp) {
            Nombre.add(crearModeloTree(resu));
        }
        return Nombre;
    }

    public void cargarCome(JTree Arbol, DataProducto dProd) {

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Comentarios");
        List<DataComentario> resp = dProd.getComents();
        for (DataComentario Come : resp) {
            rootNode.add(crearModeloTree(Come));
        }
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        Arbol.setModel(treeModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        tabbed = new javax.swing.JTabbedPane();
        infoPane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEsp = new javax.swing.JTextArea();
        txtDesc = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listCate = new javax.swing.JList();
        Img = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNRef = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtProve = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnChat = new javax.swing.JButton();
        comePane = new javax.swing.JScrollPane();
        comeTree = new javax.swing.JTree();
        recPane = new javax.swing.JPanel();
        btnReclamo = new javax.swing.JButton();
        subPaneRec = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtRec = new javax.swing.JTextArea();
        lblRec = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtResp = new javax.swing.JTextArea();
        lblNum = new javax.swing.JLabel();
        btnAnt = new javax.swing.JButton();
        btnSig = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(320, 480));
        setMinimumSize(new java.awt.Dimension(320, 480));
        setPreferredSize(new java.awt.Dimension(320, 480));

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setAutoscrolls(true);
        jScrollPane3.setMaximumSize(new java.awt.Dimension(320, 480));
        jScrollPane3.setMinimumSize(new java.awt.Dimension(320, 480));
        jScrollPane3.setName(""); // NOI18N
        jScrollPane3.setPreferredSize(new java.awt.Dimension(320, 480));

        tabbed.setMaximumSize(new java.awt.Dimension(320, 580));
        tabbed.setMinimumSize(new java.awt.Dimension(320, 580));
        tabbed.setPreferredSize(new java.awt.Dimension(320, 580));

        infoPane.setBackground(new java.awt.Color(255, 255, 255));
        infoPane.setMaximumSize(new java.awt.Dimension(300, 1000));
        infoPane.setMinimumSize(new java.awt.Dimension(300, 480));
        infoPane.setPreferredSize(new java.awt.Dimension(300, 480));
        infoPane.setLayout(null);

        txtEsp.setEditable(false);
        txtEsp.setColumns(20);
        txtEsp.setRows(5);
        txtEsp.setAutoscrolls(false);
        jScrollPane1.setViewportView(txtEsp);

        infoPane.add(jScrollPane1);
        jScrollPane1.setBounds(10, 430, 272, 78);

        txtDesc.setEditable(false);
        txtDesc.setBackground(new java.awt.Color(255, 255, 255));
        txtDesc.setText("jTextField4");
        txtDesc.setAutoscrolls(false);
        infoPane.add(txtDesc);
        txtDesc.setBounds(12, 288, 272, 30);

        jLabel6.setText("Especificacion");
        infoPane.add(jLabel6);
        jLabel6.setBounds(10, 410, 99, 15);

        jLabel7.setText("Categorias");
        infoPane.add(jLabel7);
        jLabel7.setBounds(12, 320, 78, 15);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(310, 470));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(310, 470));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(310, 470));

        listCate.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listCate.setAutoscrolls(false);
        listCate.setMaximumSize(new java.awt.Dimension(30, 85));
        listCate.setMinimumSize(new java.awt.Dimension(30, 85));
        listCate.setPreferredSize(new java.awt.Dimension(30, 85));
        jScrollPane2.setViewportView(listCate);

        infoPane.add(jScrollPane2);
        jScrollPane2.setBounds(12, 341, 272, 60);

        Img.setText("...");
        Img.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ImgMouseClicked(evt);
            }
        });
        infoPane.add(Img);
        Img.setBounds(164, 81, 120, 180);

        jLabel8.setText("Nombre");
        infoPane.add(jLabel8);
        jLabel8.setBounds(12, 60, 55, 15);

        txtNom.setEditable(false);
        txtNom.setBackground(new java.awt.Color(255, 255, 255));
        txtNom.setText("jTextField1");
        txtNom.setAutoscrolls(false);
        infoPane.add(txtNom);
        txtNom.setBounds(12, 81, 136, 30);

        jLabel3.setText("Proveedor");
        infoPane.add(jLabel3);
        jLabel3.setBounds(12, 164, 136, 15);

        txtNRef.setEditable(false);
        txtNRef.setBackground(new java.awt.Color(255, 255, 255));
        txtNRef.setText("jTextField1");
        txtNRef.setAutoscrolls(false);
        infoPane.add(txtNRef);
        txtNRef.setBounds(12, 133, 136, 30);

        jLabel4.setText("Precio");
        infoPane.add(jLabel4);
        jLabel4.setBounds(12, 216, 44, 15);

        txtProve.setEditable(false);
        txtProve.setBackground(new java.awt.Color(255, 255, 255));
        txtProve.setText("jTextField2");
        txtProve.setAutoscrolls(false);
        infoPane.add(txtProve);
        txtProve.setBounds(12, 185, 136, 30);

        jLabel2.setText("Nº Prod");
        infoPane.add(jLabel2);
        jLabel2.setBounds(12, 112, 54, 15);

        txtPrecio.setEditable(false);
        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setText("jTextField3");
        txtPrecio.setAutoscrolls(false);
        infoPane.add(txtPrecio);
        txtPrecio.setBounds(12, 237, 136, 30);

        jLabel5.setText("Descripcion");
        infoPane.add(jLabel5);
        jLabel5.setBounds(12, 268, 82, 15);

        jLabel1.setText("Ver Producto:");
        infoPane.add(jLabel1);
        jLabel1.setBounds(12, 17, 97, 15);

        btnVolver.setText("Volver");
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        infoPane.add(btnVolver);
        btnVolver.setBounds(200, 20, 78, 25);

        btnChat.setText("Chat");
        btnChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChatActionPerformed(evt);
            }
        });
        infoPane.add(btnChat);
        btnChat.setBounds(210, 520, 67, 25);

        tabbed.addTab("Informacion", infoPane);

        comeTree.setAutoscrolls(true);
        comeTree.setMaximumSize(new java.awt.Dimension(310, 470));
        comeTree.setMinimumSize(new java.awt.Dimension(310, 470));
        comeTree.setPreferredSize(new java.awt.Dimension(310, 470));
        comeTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comeTreeMouseClicked(evt);
            }
        });
        comePane.setViewportView(comeTree);

        tabbed.addTab("Comentarios", comePane);

        recPane.setBackground(new java.awt.Color(255, 255, 255));
        recPane.setMaximumSize(new java.awt.Dimension(300, 470));
        recPane.setMinimumSize(new java.awt.Dimension(300, 470));
        recPane.setPreferredSize(new java.awt.Dimension(300, 470));

        btnReclamo.setText("Reclamar");
        btnReclamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReclamoActionPerformed(evt);
            }
        });

        subPaneRec.setBackground(new java.awt.Color(255, 255, 255));
        subPaneRec.setMaximumSize(new java.awt.Dimension(247, 203));

        txtRec.setEditable(false);
        txtRec.setColumns(20);
        txtRec.setRows(1);
        txtRec.setAutoscrolls(false);
        jScrollPane4.setViewportView(txtRec);

        lblRec.setText("Reclamo:");

        jLabel10.setText("Respuesta:");

        txtResp.setEditable(false);
        txtResp.setColumns(20);
        txtResp.setRows(1);
        txtResp.setAutoscrolls(false);
        jScrollPane5.setViewportView(txtResp);

        lblNum.setText("jLabel9");

        btnAnt.setText("Anterior");
        btnAnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntActionPerformed(evt);
            }
        });

        btnSig.setText("Siguiente");
        btnSig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSigActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout subPaneRecLayout = new javax.swing.GroupLayout(subPaneRec);
        subPaneRec.setLayout(subPaneRecLayout);
        subPaneRecLayout.setHorizontalGroup(
            subPaneRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(subPaneRecLayout.createSequentialGroup()
                .addGroup(subPaneRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(subPaneRecLayout.createSequentialGroup()
                        .addComponent(lblRec)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNum))
                    .addComponent(jScrollPane4)
                    .addComponent(jLabel10)
                    .addGroup(subPaneRecLayout.createSequentialGroup()
                        .addComponent(btnAnt)
                        .addGap(18, 18, 18)
                        .addComponent(btnSig))
                    .addComponent(jScrollPane5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        subPaneRecLayout.setVerticalGroup(
            subPaneRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, subPaneRecLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(subPaneRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRec)
                    .addComponent(lblNum))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(subPaneRecLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnt)
                    .addComponent(btnSig))
                .addContainerGap())
        );

        javax.swing.GroupLayout recPaneLayout = new javax.swing.GroupLayout(recPane);
        recPane.setLayout(recPaneLayout);
        recPaneLayout.setHorizontalGroup(
            recPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subPaneRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReclamo))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        recPaneLayout.setVerticalGroup(
            recPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(subPaneRec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReclamo)
                .addGap(235, 235, 235))
        );

        tabbed.addTab("Reclamos", recPane);

        jScrollPane3.setViewportView(tabbed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        Principal.setPanel(Padre);
        hide();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void ImgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ImgMouseClicked
        // TODO add your handling code here:
        if (foto == 0) {
            if (dProd.getImg0() != null) {
                try {
                    BufferedImage BI = utilImage.toBufferedImage(dProd.getImg0());
                    ImageIcon II = utilImage.fixImagen(BI, 100, 100);
                    Img.setIcon(II);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (foto == 1) {
            if (dProd.getImg1() != null) {
                try {
                    BufferedImage BI = utilImage.toBufferedImage(dProd.getImg1());
                    ImageIcon II = utilImage.fixImagen(BI, 100, 100);
                    Img.setIcon(II);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                foto = 0;
                if (dProd.getImg0() != null) {
                    try {
                        BufferedImage BI = utilImage.toBufferedImage(dProd.getImg0());
                        ImageIcon II = utilImage.fixImagen(BI, 100, 100);
                        Img.setIcon(II);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (dProd.getImg2() != null) {
                try {
                    BufferedImage BI = utilImage.toBufferedImage(dProd.getImg2());
                    ImageIcon II = utilImage.fixImagen(BI, 100, 100);
                    Img.setIcon(II);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                foto = 0;
                if (dProd.getImg0() != null) {
                    try {
                        BufferedImage BI = utilImage.toBufferedImage(dProd.getImg0());
                        ImageIcon II = utilImage.fixImagen(BI, 100, 100);
                        Img.setIcon(II);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        foto = (foto + 1) % 3;
    }//GEN-LAST:event_ImgMouseClicked

    private void comeTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comeTreeMouseClicked
    }//GEN-LAST:event_comeTreeMouseClicked

    private void btnChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChatActionPerformed
        // TODO add your handling code here:
        Chat.setnProv(dProd.getProveedor());
        try {
            setPanel(new Chat());
        } catch (Exception e) {
            Principal.MostrarMensaje("Imposible conectar con el servidor.", "Error");
        }

    }//GEN-LAST:event_btnChatActionPerformed

    private void btnSigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSigActionPerformed
        // TODO add your handling code here:
        iRec = (iRec - 1 + maxRec) % maxRec;
        DataReclamo r = reclamosCli.get(iRec);
        txtRec.setText(r.getTexto());
        txtResp.setText(r.getRespuesta());
        Integer num = (maxRec - iRec);
        lblNum.setText(num.toString() + "/" + (maxRec).toString());
    }//GEN-LAST:event_btnSigActionPerformed

    private void btnAntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntActionPerformed
        // TODO add your handling code here:
        iRec = (iRec + 1) % maxRec;
        DataReclamo r = reclamosCli.get(iRec);
        txtRec.setText(r.getTexto());
        txtResp.setText(r.getRespuesta());
        Integer num = (maxRec - iRec);
        lblNum.setText(num.toString() + "/" + (maxRec).toString());
    }//GEN-LAST:event_btnAntActionPerformed

    private void btnReclamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReclamoActionPerformed
        Principal.setPanel(new AltaReclamo());
    }//GEN-LAST:event_btnReclamoActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Img;
    private javax.swing.JButton btnAnt;
    private javax.swing.JButton btnChat;
    private javax.swing.JButton btnReclamo;
    private javax.swing.JButton btnSig;
    private javax.swing.JButton btnVolver;
    private javax.swing.JScrollPane comePane;
    private javax.swing.JTree comeTree;
    private javax.swing.JPanel infoPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblNum;
    private javax.swing.JLabel lblRec;
    private javax.swing.JList listCate;
    private javax.swing.JPanel recPane;
    private javax.swing.JPanel subPaneRec;
    private javax.swing.JTabbedPane tabbed;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextArea txtEsp;
    private javax.swing.JTextField txtNRef;
    private javax.swing.JTextField txtNom;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProve;
    private javax.swing.JTextArea txtRec;
    private javax.swing.JTextArea txtResp;
    // End of variables declaration//GEN-END:variables
}
