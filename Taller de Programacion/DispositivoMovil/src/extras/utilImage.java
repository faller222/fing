package extras;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class utilImage {

    private static BufferedImage toBufferedImage(Image src) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

    public static Image toImage(BufferedImage src) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

    public static BufferedImage toBufferedImage(byte[] bytes) throws Exception {
        if (bytes == null) {
            return null;
        }
        InputStream in = new ByteArrayInputStream(bytes);
        return ImageIO.read(in);
    }

    public static byte[] toByteArray(BufferedImage BI) throws Exception {
        if (BI == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(BI, "jpg", baos);
        baos.flush();
        byte[] ret = baos.toByteArray();
        baos.close();
        return ret;
    }

    public static Image toImage(byte[] bytes) throws Exception {
        return toImage(toBufferedImage(bytes));
    }

    public static BufferedImage toBufferedImage(String path) throws Exception {
        if (path == null) {
            return null;
        }
        return ImageIO.read(new File(path));
    }

    public static byte[] toByteArray(String path) throws Exception {
        if (path == null) {
            return null;
        }
        BufferedImage BI = ImageIO.read(new File(path));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(BI, getExt(path), baos);
        baos.flush();
        byte[] ret = baos.toByteArray();
        baos.close();
        return ret;
    }

//    public static byte[] toByteArray(String path) throws Exception {
//        if (path == null) {
//            return null;
//        }
//        return toByteArray(toBufferedImage(path));
//    }
    private static String getExt(String path) throws Exception {
        return path.substring(path.lastIndexOf('.') + 1);
    }

    public static ImageIcon fixImagen(BufferedImage Imagen, int h, int w) throws IOException {
        Image Aux;

        float Alto = Imagen.getHeight(null);
        float Ancho = Imagen.getWidth(null);
        float prop = Alto / Ancho;
        float AuxH = Alto / h;
        float AuxW = Ancho / w;

        if (AuxH > AuxW) {
            Alto = h;
            Ancho = Alto / prop;
        } else {
            Ancho = w;
            Alto = Ancho * prop;
        }

        Aux = Imagen.getScaledInstance((int) Ancho, (int) Alto, Image.SCALE_DEFAULT);
        return new ImageIcon(Aux);
    }

    public static ImageIcon fixImagen(String Path, int h, int w) throws IOException {
        BufferedImage Imagen = ImageIO.read(new File(Path));
        return fixImagen(Imagen, h, w);
    }
}
