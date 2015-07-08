/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.servlet.image;

import java.awt.print.PrinterJob;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.ColorSupported;
import javax.print.attribute.standard.PrinterName;

/**
 *
 * @author Juan
 */
public class PrintingFile {

    public void printingImage(String filePath) throws FileNotFoundException, PrintException, IOException {
        FileInputStream inputStream = new FileInputStream(filePath);

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrinterJob job = PrinterJob.getPrinterJob();
        job.printDialog();
        String printerName = job.getPrintService().getName();

        //Inclusion del nombre de impresora y sus atributos
        AttributeSet attributeSet = new HashAttributeSet();
        attributeSet.add(new PrinterName(printerName, null));
        attributeSet = new HashAttributeSet();
        //Soporte de color o no
        attributeSet.add(ColorSupported.NOT_SUPPORTED);

        //Busqueda de la impresora por el nombre asignado en attributeSet
        PrintService[] services = PrintServiceLookup.lookupPrintServices(docFormat, attributeSet);

        System.out.println("Imprimiendo en : " + services[0].getName());

        DocPrintJob printJob = services[0].createPrintJob();
        //Envio a la impresora
        printJob.print(document, new HashPrintRequestAttributeSet());

        inputStream.close();
    }
}
