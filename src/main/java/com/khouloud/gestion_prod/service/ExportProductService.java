package com.khouloud.gestion_prod.service;

import com.itextpdf.text.Font;
import com.khouloud.gestion_prod.model.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
            Paragraph para=new Paragraph("Products List PDF",font);
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

    public static ByteArrayInputStream productsExcel(List<Product> products) throws IOException {
        String[] columns = {"name","description","prix","quantite","date Creation","dateUpdateP"};
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            CreationHelper creationHelper=workbook.getCreationHelper();

            XSSFSheet sheet= workbook.createSheet("products");


            sheet.autoSizeColumn(columns.length);

            org.apache.poi.ss.usermodel.Font headerFont=workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle cellStyle=workbook.createCellStyle();
            cellStyle.setFont(headerFont);

            //row fo header
            Row headerRow=sheet.createRow(0);
            //Header
            for (int col=0;col<columns.length;col++){
                Cell cell= headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(cellStyle);
            }
            CellStyle cellStyle1=workbook.createCellStyle();
            cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("#"));
            int rowIndex=1;
            for (Product product:products){
                Row row=sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(product.getNom());
                row.createCell(1).setCellValue(product.getDescription());
                row.createCell(2).setCellValue(String.valueOf(product.getPrix()));
                row.createCell(3).setCellValue(String.valueOf(product.getQuantite()));
                row.createCell(4).setCellValue(String.valueOf(product.getDateP()));
                row.createCell(5).setCellValue(String.valueOf(product.getDateUpdate()));

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}