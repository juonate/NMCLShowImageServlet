/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.PrintException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Juan
 */
public class ShowImage extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, FileNotFoundException {
        response.setContentType("image/png");
        String pathImage = request.getParameter("path");
        String pathSE = pathImage.substring(0, pathImage.length() - 4);
        String pathThumb = pathSE + "thumb.png";
        File fileThumb = new File(pathThumb);
        
        if(!fileThumb.exists()){
            System.out.println("Creando thumbnail de la imagen... thumbnail creado: " + pathThumb );
            ImageResizer rImage = new ImageResizer(700);
            ImageResizer.copiarImagen(pathImage, pathThumb);
            BufferedImage imagenCargada = ImageResizer.cargarImagen(pathThumb);
            ImageResizer.resize(imagenCargada, 100, 100);
            ImageResizer.GuardarImagen(imagenCargada, pathThumb);

            OutputStream os = response.getOutputStream();
            ImageIO.write(imagenCargada, "png", os);
            PrintingFile pf = new PrintingFile();
            try {
                pf.printingImage(pathThumb);
            } catch (PrintException ex) {
                Logger.getLogger(ShowImage.class.getName()).log(Level.SEVERE, null, ex);
            }
            os.close();
        } else {
            System.out.println("Archivo thumbnail ya fue creado... abriendo archivo: " + pathThumb);
            BufferedImage imagenCargada = ImageResizer.cargarImagen(pathThumb);
            OutputStream os = response.getOutputStream();
            ImageIO.write(imagenCargada, "png", os);
            PrintingFile pf = new PrintingFile();
            try {
                pf.printingImage(pathThumb);
            } catch (PrintException ex) {
                Logger.getLogger(ShowImage.class.getName()).log(Level.SEVERE, null, ex);
            }
            os.close();
        }
        
    }
}
