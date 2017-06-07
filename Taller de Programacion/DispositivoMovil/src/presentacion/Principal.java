package presentacion;

import adapter.ASesion;
import conexion.CSesion;
import conexion.Conexion;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.sound.midi.MidiDevice;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Principal extends javax.swing.JFrame {
    // <editor-fold defaultstate="collapsed" desc="Menu opciones, Fito">

    javax.swing.JPopupMenu menu = new javax.swing.JPopupMenu("Popup");

    class MyPanel extends javax.swing.JPanel {

        public MyPanel() {
            addMouseListener(new Principal.MyPanel.PopupTriggerListener());
        }

        class PopupTriggerListener extends java.awt.event.MouseAdapter {

            @Override
            public void mousePressed(java.awt.event.MouseEvent ev) {
                if (ev.isPopupTrigger()) {
                    menu.show(ev.getComponent(), ev.getX(), ev.getY());
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent ev) {
                if (ev.isPopupTrigger()) {
                    menu.show(ev.getComponent(), ev.getX(), ev.getY());
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent ev) {
            }
        }
    }//</editor-fold>
    public static JPanel visiblePane;
    private static Principal Form = null;
    private static Conexion con = null;

    public static void MostrarMensaje(String Mensaje, String Titulo) {
        JOptionPane.showMessageDialog(Form,
                Mensaje,
                Titulo,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static Principal getPrincipal() {
        if (Form == null) {
            Form = new Principal();
        }
        return Form;
    }

    public static void setPanel(JPanel com) {
        if (visiblePane != null) {
            visiblePane.setVisible(false);
        }
        com.setSize(new Dimension(320, 480));
        com.setLocation(new Point(26, 100));
        visiblePane = com;

        Form.add(visiblePane);
        visiblePane.setVisible(false);
        visiblePane.setVisible(true);
    }

    public static Conexion getConexion() {
        if (con == null) {
            try {
                con = new Conexion();
            } catch (Exception e) {
            }
        }
        return con;
    }

    public static void setConexion(Conexion cn) {
        con = cn;
    }

    private Principal() {

        try {
            this.setIconImage(ImageIO.read(new File("src/imagenes/icono.png")));
            //Image movil = ImageIO.read(new File("src/imagenes/DirectMarket.jpg"));
            //this.setContentPane(new ImagePanel(movil));
            this.setContentPane(new JLabel(new ImageIcon("src/imagenes/DirectMarket.jpg")));
        } catch (IOException e) {
        }

        initComponents();

        FlowLayout loi = (new FlowLayout());
        loi.setVgap(100);
        this.setLayout(loi);
        // <editor-fold defaultstate="collapsed" desc="Menu opciones, Fito">
        javax.swing.JMenuItem item = new javax.swing.JMenuItem("Configuracion");

        item.addActionListener(
                new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Form.add(new Configuracion(visiblePane));
            }
        });
        menu.add(item);
        // </editor-fold>

        setVisible(true);
    }

    private static void PreguntarMens() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (CSesion.getInstance().tengoMsj()) {
                        MostrarMensaje("Te ha llegado un nuevo mensaje", "Mensajito");
                        Chat.setnProv("");
                        setPanel(new Chat());
                    }
                } catch (Exception ex) {
                }
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 3000);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Direct Market");
        setBounds(new java.awt.Rectangle(100, 26, 101, 26));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(372, 677));
        setMinimumSize(new java.awt.Dimension(372, 677));
        setName("principalFrame"); // NOI18N
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 372, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        extras.Configuracion.load();
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
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Principal p = getPrincipal();
        if (CSesion.getInstance().verInfoPerfil() == null) {
            setPanel(new InicioSesion());
        } else {
            setPanel(new InfoPerfil());
        }
        PreguntarMens();

    }
// <editor-fold defaultstate="collapsed" desc="Variables">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
//</editor-fold>
}