package com.khouloud.gestion_prod.service;

import com.khouloud.gestion_prod.model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;


@Service
public class ExportProductService {
    public static ByteArrayInputStream productsPDF(List<Product> products){
        Document document=new Document();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document,out);
            document.open();

            //add text to pdf file
            com.itextpdf.text.Font font= FontFactory.getFont(FontFactory.COURIER,14, BaseColor.BLACK);
            Paragraph para=new Paragraph("Prducts List PDF",font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table=new PdfPTable(6);
            Stream.of("name","description","prix","quantite","date Creation","dateUpdateP").forEach(headerTitle -> {
                PdfPCell header=new PdfPCell();
                com.itextpdf.text.Font headFont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle,headFont));
                table.addCell(header);
            });
            for(Product prod:products){
                PdfPCell titleCell=new PdfPCell(new Phrase(prod.getNom()));
                titleCell.setPaddingLeft(1);
                titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(titleCell);

                PdfPCell descCell=new PdfPCell(new Phrase(prod.getDescription()));
                descCell.setPaddingLeft(1);
                descCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                descCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(descCell);

                PdfPCell prixUniPCell=new PdfPCell(new Phrase(String.valueOf(prod.getPrix())));
                prixUniPCell.setPaddingLeft(1);
                prixUniPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                prixUniPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(prixUniPCell);

                PdfPCell quantitePCell=new PdfPCell(new Phrase(String.valueOf(prod.getQuantite())));
                quantitePCell.setPaddingLeft(1);
                quantitePCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                quantitePCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(quantitePCell);

                PdfPCell dateCreationPCell=new PdfPCell(new Phrase(String.valueOf(prod.getDateP())));
                dateCreationPCell.setPaddingLeft(1);
                dateCreationPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                dateCreationPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(dateCreationPCell);

                PdfPCell dateUpdatePCell=new PdfPCell(new Phrase(String.valueOf(prod.getDateUpdate())));
                dateUpdatePCell.setPaddingLeft(1);
                dateUpdatePCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                dateUpdatePCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(dateUpdatePCell);
            }
            document.add(table);
            document.close();
        } catch (com.itextpdf.text.DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}