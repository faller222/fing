package Presentacion;

import DataTypes.DataCategoria;
import Interfaces.Fabrica;
import Interfaces.IAgregarCategoria;
import Extras.Utils;
import Interfaces.IAcceso;
import Publisher.Publicador;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Presentacion extends javax.swing.JFrame {

    public static void main(String args[]) {
        //Levanto los Web Services
        Publicador.publicarServicios();
        //cargo los datos
        Utils.Cargar();



        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Presentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Presentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Presentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Presentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Principal = new Presentacion();
            }
        });
        //</editor-fold>
    }
    //esto es para acceder desde otro internal
    private static Presentacion Principal;

    public static Presentacion getPrincipal() {
        return Principal;
    }

    public Presentacion() {
        initComponents();
        try {
            this.setIconImage(ImageIO.read(new File("src/Imagenes/Icono.png")));
        } catch (IOException e) {
        }
        this.setContentPane(new JLabel(new ImageIcon("src/Imagenes/Banner.gif")));
        this.setLayout(new FlowLayout());
        this.setVisible(true);
    }

    public static ImageIcon fixImagen(BufferedImage Imagen, Component componente) throws IOException {
        Image Aux;

        float Alto = Imagen.getHeight(componente);
        float Ancho = Imagen.getWidth(componente);
        float prop = Alto / Ancho;
        float AuxH = Alto / componente.getHeight();
        float AuxW = Ancho / componente.getWidth();

        if (AuxH > AuxW) {
            Alto = componente.getHeight();
            Ancho = Alto / prop;
        } else {
            Ancho = componente.getWidth();
            Alto = Ancho * prop;
        }


        Aux = Imagen.getScaledInstance((int) Ancho, (int) Alto, Image.SCALE_DEFAULT);
        return new ImageIcon(Aux);
    }

    public static ImageIcon fixImagen(String Path, Component componente) throws IOException {
        BufferedImage Imagen = ImageIO.read(new File(Path));
        return fixImagen(Imagen, componente);
    }

    private DefaultMutableTreeNode crearModeloTree(DataCategoria cata) {
        DefaultMutableTreeNode Resultado = new DefaultMutableTreeNode(cata.getNombre());
        ArrayList<DataCategoria> hijas = cata.getCateHijas();
        for (DataCategoria dataCategoria : hijas) {
            Resultado.add(crearModeloTree(dataCategoria));
        }
        return Resultado;
    }

    public void cargarArbol(JTree Arbol) {
        IAgregarCategoria IC = Fabrica.getIAC();
        DefaultMutableTreeNode rootNode = crearModeloTree(IC.listarCategorias());
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        Arbol.setModel(treeModel);

    }

    public static void SwapMenu() {
        boolean Flag = !Principal.MenuUsuario.isEnabled();

        Principal.MenuUsuario.setEnabled(Flag);
        Principal.MenuProd.setEnabled(Flag);
        Principal.MenuCate.setEnabled(Flag);
        Principal.MenuOrden.setEnabled(Flag);
        Principal.MenuEx.setEnabled(Flag);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        MenuBarra = new javax.swing.JMenuBar();
        MenuUsuario = new javax.swing.JMenu();
        MenuAgrClie = new javax.swing.JMenuItem();
        MenuVerClie = new javax.swing.JMenuItem();
        MenuVerProv = new javax.swing.JMenuItem();
        MenuProd = new javax.swing.JMenu();
        MenuModProd = new javax.swing.JMenuItem();
        MenuVerProd = new javax.swing.JMenuItem();
        MenuCate = new javax.swing.JMenu();
        MenuAgrCate = new javax.swing.JMenuItem();
        MenuOrden = new javax.swing.JMenu();
        MenuAgrOrden = new javax.swing.JMenuItem();
        MenuVerOrden = new javax.swing.JMenuItem();
        MenuEx = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        SwapServis = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenuItem4.setText("jMenuItem4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Direct Market");
        setBackground(new java.awt.Color(238, 69, 32));
        setForeground(new java.awt.Color(255, 177, 144));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        MenuUsuario.setText("Usuario");

        MenuAgrClie.setText("Agregar Usuario");
        MenuAgrClie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAgrClieActionPerformed(evt);
            }
        });
        MenuUsuario.add(MenuAgrClie);

        MenuVerClie.setText("Ver Info Cliente");
        MenuVerClie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuVerClieActionPerformed(evt);
            }
        });
        MenuUsuario.add(MenuVerClie);

        MenuVerProv.setText("Ver Info Proveedor");
        MenuVerProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuVerProvActionPerformed(evt);
            }
        });
        MenuUsuario.add(MenuVerProv);

        MenuBarra.add(MenuUsuario);

        MenuProd.setText("Producto");

        MenuModProd.setText("Modificar");
        MenuModProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuModProdActionPerformed(evt);
            }
        });
        MenuProd.add(MenuModProd);

        MenuVerProd.setText("Ver Informacion");
        MenuVerProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuVerProdActionPerformed(evt);
            }
        });
        MenuProd.add(MenuVerProd);

        MenuBarra.add(MenuProd);

        MenuCate.setText("Categoria");

        MenuAgrCate.setText("Agregar");
        MenuAgrCate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAgrCateActionPerformed(evt);
            }
        });
        MenuCate.add(MenuAgrCate);

        MenuBarra.add(MenuCate);

        MenuOrden.setText("Orden de Compra");

        MenuAgrOrden.setText("Agregar");
        MenuAgrOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuAgrOrdenActionPerformed(evt);
            }
        });
        MenuOrden.add(MenuAgrOrden);

        MenuVerOrden.setText("Ver Informacion");
        MenuVerOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuVerOrdenActionPerformed(evt);
            }
        });
        MenuOrden.add(MenuVerOrden);

        MenuBarra.add(MenuOrden);

        MenuEx.setText("Extras");

        jMenuItem1.setText("Ver Accesos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        MenuEx.add(jMenuItem1);

        jMenuItem2.setText("Simular");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        MenuEx.add(jMenuItem2);
        MenuEx.add(jSeparator1);

        SwapServis.setText("Detener Servicios");
        SwapServis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SwapServisActionPerformed(evt);
            }
        });
        MenuEx.add(SwapServis);

        jMenuItem3.setText("Refrescar Servicios");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        MenuEx.add(jMenuItem3);

        jMenuItem5.setText("Propiedades");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        MenuEx.add(jMenuItem5);

        MenuBarra.add(MenuEx);

        setJMenuBar(MenuBarra);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 988, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MenuVerClieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuVerClieActionPerformed
        Principal.add(new VerInfoCliente());
        SwapMenu();
    }//GEN-LAST:event_MenuVerClieActionPerformed

    private void MenuVerProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuVerProvActionPerformed
        Principal.add(new VerInfoProveedor());
        SwapMenu();
    }//GEN-LAST:event_MenuVerProvActionPerformed

    private void MenuAgrClieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAgrClieActionPerformed
        Principal.add(new AddUsuario());
        SwapMenu();
    }//GEN-LAST:event_MenuAgrClieActionPerformed

    private void MenuModProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuModProdActionPerformed
        Principal.add(new ModProd());
        SwapMenu();
    }//GEN-LAST:event_MenuModProdActionPerformed

    private void MenuVerProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuVerProdActionPerformed
        Principal.add(new VerProd());
        SwapMenu();
    }//GEN-LAST:event_MenuVerProdActionPerformed

    private void MenuAgrCateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAgrCateActionPerformed
        Principal.add(new AddCate());
        SwapMenu();
    }//GEN-LAST:event_MenuAgrCateActionPerformed

    private void MenuAgrOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuAgrOrdenActionPerformed
        Principal.add(new AddOrden());
        SwapMenu();
    }//GEN-LAST:event_MenuAgrOrdenActionPerformed

    private void MenuVerOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuVerOrdenActionPerformed
        Principal.add(new CanOrden(false));
        SwapMenu();
    }//GEN-LAST:event_MenuVerOrdenActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Principal.add(new VerAcceso());
        SwapMenu();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        IAcceso ca = Fabrica.getIA();
        ca.DatosDePrueba(99999);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        Publicador.refreshServicios();
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        Principal.add(new PropiedadesFrame());
        SwapMenu();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void SwapServisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SwapServisActionPerformed
        if (Publicador.isPublicados()) {
            Publicador.stop();
            SwapServis.setText("Publicar Servicios");
        } else {
            Publicador.publicarServicios();
            SwapServis.setText("Detener Servicios");
        }
    }//GEN-LAST:event_SwapServisActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem MenuAgrCate;
    private javax.swing.JMenuItem MenuAgrClie;
    private javax.swing.JMenuItem MenuAgrOrden;
    private javax.swing.JMenuBar MenuBarra;
    private javax.swing.JMenu MenuCate;
    private javax.swing.JMenu MenuEx;
    private javax.swing.JMenuItem MenuModProd;
    private javax.swing.JMenu MenuOrden;
    private javax.swing.JMenu MenuProd;
    private javax.swing.JMenu MenuUsuario;
    private javax.swing.JMenuItem MenuVerClie;
    private javax.swing.JMenuItem MenuVerOrden;
    private javax.swing.JMenuItem MenuVerProd;
    private javax.swing.JMenuItem MenuVerProv;
    private javax.swing.JMenuItem SwapServis;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
