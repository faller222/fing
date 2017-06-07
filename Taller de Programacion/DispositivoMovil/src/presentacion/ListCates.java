/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import adapter.ABuscarCate;
import adapter.AVerProd;
import conexion.CSesion;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import static presentacion.Principal.setPanel;
import publisher.DataCategoria;
import publisher.DataProducto;

/**
 *
 * @author tprog080
 */
public class ListCates extends javax.swing.JPanel {

    List<DataProducto> Productos;

    public ListCates() {
        initComponents();
        cargarArbol(Arbol);
    }

    private DefaultMutableTreeNode crearModeloTree(DataCategoria cata) {
        DefaultMutableTreeNode Resultado = new DefaultMutableTreeNode(cata.getNombre());
        List<DataCategoria> hijas = cata.getCateHijas();
        for (DataCategoria dataCategoria : hijas) {
            Resultado.add(crearModeloTree(dataCategoria));
        }
        return Resultado;
    }

    public void cargarArbol(JTree Arbol) {
        ABuscarCate IC = new ABuscarCate();
        DefaultMutableTreeNode rootNode = crearModeloTree(IC.listarCategorias());
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

        jScrollPane1 = new javax.swing.JScrollPane();
        Arbol = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        Arbol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ArbolMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Arbol);

        jLabel1.setText("Seleccione la categoria:");

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jButton1))
                        .addGap(0, 126, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ArbolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ArbolMouseClicked

        AVerProd IVMP = new AVerProd();

        if (Arbol.getLeadSelectionPath() != null) {
            try {
                String seleccion = Arbol.getLeadSelectionPath().getLastPathComponent().toString();
                if (IVMP.seleccionarCategoria(seleccion).isEsSimple()) {

                    Productos = IVMP.listarProductosCategoria();
                    Principal.setPanel(new ListProds(Productos));
                    hide();
                }
            } catch (Exception ex) {
                Logger.getLogger(ListCates.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_ArbolMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            if (CSesion.getInstance().verInfoPerfil() == null) {
                setPanel(new InicioSesion());
            } else {
                setPanel(new InfoPerfil());
            }
            hide();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree Arbol;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}