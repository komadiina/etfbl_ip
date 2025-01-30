package org.unibl.etf.promotionapp.db.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.unibl.etf.promotionapp.beans.RentalInfo;
import org.unibl.etf.promotionapp.db.models.Client;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFService {
    public static void generatePDF(Client client, RentalInfo rentalInfo, String fileName,
                                   HttpServletRequest request, HttpServletResponse response)
    {
        try {
            // params
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD);

            // init content
            document.open();
            document.add(new Chunk("Invoice for " + client.getFirstName() + " " + client.getLastName(), font));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Rental ID: " + rentalInfo.getRentalID()));
            document.add(new Paragraph("Start date: " + rentalInfo.getStartDateTime()));
            document.add(new Paragraph("Model: " + rentalInfo.getDevice().getModel()));
            document.add(new Paragraph("Device UUID:" + rentalInfo.getDevice().getUUID()));
            document.add(new Paragraph("Rate (PPM): " + rentalInfo.getPrice().getPricePerMinute() + "$"));
            document.close();

            // MORAM PREKO BAJTOVA JOJ BOZE DRAGI POMOZI
            byte[] pdfBytes = baos.toByteArray();

            // set headers
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setContentLength(pdfBytes.length);

            // write content
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
        }
    }
}
