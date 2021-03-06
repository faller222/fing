/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import conexion.Conexion;
import static extras.utilDate.formatS;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import publisher.DataReclamo;

/**
 *
 * @author Tito
 */
public class ListReclamos extends javax.swing.JPanel {

    List<dataType.DataReclamo> Datas;

    public ListReclamos() {
        initComponents();
        setVisible(true);
        try {
            Conexion c = Principal.getConexion();
            Datas = c.getReclamos();
            resetTablaReclamos(tablaReclamos);
        } catch (Exception e) {
        }
    }

    private void resetTablaReclamos(JTable TablaO) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        modelo.setColumnIdentifiers(new Object[]{"Fecha", "Numero Prod", "Producto"});
        for (Iterator<dataType.DataReclamo> it = Datas.iterator(); it.hasNext();) {
            dataType.DataReclamo dR = it.next();
            Date F = dR.getFecha();
            modelo.addRow(new Object[]{formatS(F), dR.getnRef(), dR.getNomProd()});
        }
        TablaO.setModel(modelo);
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
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaReclamos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtContenido = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(310, 470));
        jPanel1.setMinimumSize(new java.awt.Dimension(310, 470));
        jPanel1.setPreferredSize(new java.awt.Dimension(310, 470));

        tablaReclamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaReclamos.setFillsViewportHeight(true);
        tablaReclamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaReclamosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaReclamos);

        jLabel2.setText("Cliente");

        txtCliente.setEditable(false);
        txtCliente.setAutoscrolls(false);

        txtContenido.setEditable(false);
        txtContenido.setColumns(20);
        txtContenido.setRows(5);
        txtContenido.setAutoscrolls(false);
        jScrollPane2.setViewportView(txtContenido);

        jLabel1.setText("Reclamo");

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Principal.setPanel(new InfoPerfil());
            hide();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablaReclamosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaReclamosMouseClicked
        Integer ind = tablaReclamos.getSelectedRow();
        dataType.DataReclamo dR = Datas.get(ind);
        txtCliente.setText(dR.getCliente());
        txtContenido.setText(dR.getTexto());
        Integer num = Integer.parseInt(tablaReclamos.getValueAt(tablaReclamos.getSelectedRow(), 1).toString());





    }//GEN-LAST:event_tablaReclamosMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTable tablaReclamos;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextArea txtContenido;
    // End of variables declaration//GEN-END:variables
}
