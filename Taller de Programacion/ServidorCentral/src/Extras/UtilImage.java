package Extras;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class UtilImage {

    public String copiarImagenServidor(String path, String imageName) throws IOException {
        //BufferedImage src = ImageIO.read(new File(path));
        BufferedImage src = fito(path);//Fito obveo
        // Convert Image to BufferedImage if required.
        BufferedImage image = toBufferedImage(src);
        // png okay, j2se 1.4+
        //save(image, "bmp", path);  // j2se 1.5+
        // gif okay in j2se 1.6+
        return save(image, "jpg", imageName);
    }

    private String save(BufferedImage image, String ext, String imageName) {
        URL url = this.getClass().getClassLoader().getResource("");
        String thing = url.getFile();
        String fileName = thing + "/Imagenes/" + imageName + "." + ext;
        File file = new File(fileName);
        try {
            ImageIO.write(image, ext, file);  // ignore returned boolean
        } catch (IOException e) {
            System.out.println("Write error for " + file.getPath()
                    + ": " + e.getMessage());
        }
        return fileName;
    }

    private BufferedImage fito(String path) throws IOException {
        URL url = this.getClass().getClassLoader().getResource(path);
        String thing = url.getFile();
        return ImageIO.read(new File(thing));
    }

    public String Path() {
        URL url = this.getClass().getClassLoader().getResource("");
        return url.getFile();
    }

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
}
