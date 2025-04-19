package com.devtony.app.services.utils;

import com.devtony.app.exception.ExceptionDetails;
import com.devtony.app.exception.UserException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

@Component
public class QRGenerator {
    public BufferedImage generarCodigoQR(String texto, int ancho, int alto) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BufferedImage imagenQR = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, ancho, alto);
            for (int x = 0; x < ancho; x++) {
                for (int y = 0; y < alto; y++) {
                    imagenQR.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

        } catch (WriterException e) {
            throw new UserException("Can't generate QR code",
                    new ExceptionDetails("No se puede generar el código QR", "medium"));
        }
        return imagenQR;
    }

    public byte[] convertirImagenABytes(BufferedImage imagenQR) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(imagenQR, "png", baos);  // Puedes cambiar "png" por "jpg" si prefieres otro formato
            return baos.toByteArray();
        } catch (IOException e) {
            throw new UserException("Can't convert image to bytes",
                    new ExceptionDetails("No se puede convertir la imagen a bytes", "medium"));
        }
    }

    public String convertirImagenBase64(BufferedImage imagenQR) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(imagenQR, "png", baos);  // Puedes cambiar "png" por "jpg" si prefieres otro formato
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            throw new UserException("Can't convert image to base64",
                    new ExceptionDetails("No se puede convertir la imagen a base64", "medium"));
        }
    }

    public void guardarQR(String texto) {
        try {

            // Generar el QR
            BitMatrix matrix = new MultiFormatWriter().encode(texto, BarcodeFormat.QR_CODE, 300, 300);

            // Crear una imagen en blanco
            BufferedImage imagenQR = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            imagenQR.createGraphics().fillRect(0, 0, 300, 300);

            // Rellenar la imagen con el contenido del QR
            for (int y = 0; y < 300; y++) {
                for (int x = 0; x < 300; x++) {
                    imagenQR.setRGB(x, y, (matrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB()));
                }
            }

            // Definir la ruta donde almacenar el archivo
            String rutaArchivo = "src/UsersFiles/" + texto + ".png";
            File archivo = new File(rutaArchivo);

            // Guardar la imagen como archivo PNG
            ImageIO.write(imagenQR, "PNG", archivo);

            // Devolver la URL o ruta relativa del archivo

        } catch (WriterException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el QR");
        }
    }

    public void deleteQR(String texto) {
        try {
            // Definir la ruta donde almacenar el archivo
            String rutaArchivo = "src/UsersFiles/" + texto + ".png";
            File archivo = new File(rutaArchivo);
            archivo.delete();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar el QR");
        }
    }
}
