package com.portale.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import com.portale.model.ItemObject;

@Service
@Repository
public class PDFService {
	@Value("${local.dir.pdf}")
	private String pdf_path;
	@Value("${local.dir.archive}")
	private String images_path;

	public String UserPrint(ItemObject itemObject) throws Exception {
		String pdfname = itemObject.getItem_name().replaceAll(" ",  "_")+".pdf";
		String pdfOutput = pdf_path+"//"+ pdfname;
		PdfWriter writer = new PdfWriter(pdfOutput);
		PdfDocument pdfDocument = new PdfDocument(writer);
		pdfDocument.setDefaultPageSize(PageSize.A4);

		Document doc = new Document(pdfDocument);

		Table table = new Table(UnitValue.createPercentArray(new float[]{100}));
		table.setWidth(UnitValue.createPercentValue(100));
			
		Cell cell1 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER).setFontSize(23F);
		Cell cell2 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER).setFontSize(13F);
		Cell cell3 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER).setFontSize(13F);
		Cell cell4 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.LEFT).setFontSize(11F);

		Paragraph paragarph1 = new Paragraph("Materialeinvendita");
		Paragraph paragarph2 = new Paragraph(itemObject.getType() == 2 ?  "Servizi" : "Prodotti");
		Paragraph paragarph3 = new Paragraph(itemObject.getItem_name());
		Paragraph paragarph4 = new Paragraph(itemObject.getItem_description());

		cell1.add(paragarph1);
		cell2.add(paragarph2);
		cell3.add(paragarph3);
		cell4.add(paragarph4);

		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		
		Table table_images = new Table(UnitValue.createPercentArray(new float[]{100,100,100,100}));
		table_images.setKeepTogether(true);
		table_images.setWidth(UnitValue.createPercentValue(100));
		Cell img_cell[] = new Cell[itemObject.getItem_media().size()];
		for(int i = 0; i < img_cell.length; i++) {
			if(itemObject.getItem_media().get(i).getMedia_id() != null) {
				String sourceImage = images_path + "//" + itemObject.getItem_media().get(i).getMedia_owner() + "//" + itemObject.getItem_media().get(i).getMedia_path();
				System.out.println(sourceImage);
				ImageData imageData = ImageDataFactory.create(sourceImage); 
				Image img = new Image(imageData); 
				img_cell[i] = new Cell().setBorder(new SolidBorder(1))
						.add(img.setAutoScale(true)
								.setHorizontalAlignment(HorizontalAlignment.CENTER)).setVerticalAlignment(VerticalAlignment.MIDDLE).setHeight(100);
				table_images.addCell(img_cell[i]);	
			}
		}
		
		doc.add(table);
		if(itemObject.getType() == 1){
			Table detail_table = new Table(UnitValue.createPercentArray(new float[]{100,100,100,100}));
			detail_table.setWidth(UnitValue.createPercentValue(100));
				
			Cell detail_cell1 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER).setFontSize(10F);
			Cell detail_cell2 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER).setFontSize(10F);
			Cell detail_cell3 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER).setFontSize(10F);
			Cell detail_cell4 = new Cell().setBorder(new SolidBorder(1)).setTextAlignment(TextAlignment.CENTER).setFontSize(10F);
			
			Paragraph detail_paragarph1 = new Paragraph("Da vedere");
			
			if(itemObject.getUnit_price() != null) {
				NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.ITALY);
				detail_paragarph1 = new Paragraph(nf.format(itemObject.getUnit_price()/100.0));
			}
			
			
			Paragraph detail_paragarph2 = new Paragraph(itemObject.getShipment_included() == true ? "Spedizione inclusa" : "Spedizione a parte");
			Paragraph detail_paragarph3 = new Paragraph(itemObject.getQuantity() > 0 ? "Disponibile" : "Non disponibile");
			Paragraph detail_paragarph4 = new Paragraph(itemObject.getQuality());

			detail_cell1.add(detail_paragarph1);
			detail_cell2.add(detail_paragarph2);
			detail_cell3.add(detail_paragarph3);
			detail_cell4.add(detail_paragarph4);

			detail_table.addCell(detail_cell1);
			detail_table.addCell(detail_cell2);
			detail_table.addCell(detail_cell3);
			detail_table.addCell(detail_cell4);
			doc.add(detail_table);

		}
		doc.add(table_images);
		
		doc.close();
		
		Files.setPosixFilePermissions(
				Paths.get(pdfOutput),
				PosixFilePermissions.fromString("rw-rw-r--"));
		
		return pdfname;
	}

}
