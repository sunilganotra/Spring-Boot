package com.amzn.service.impl;
import com.amzn.controller.BookController;
import com.amzn.controller.BookController.ISBNRequest;
import com.amzn.service.BookService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
import java.io.FileInputStream;

import java.io.File; 
import java.io.FileInputStream; 
import java.io.InputStream;

@Service
public class BookServiceImpl implements BookService {
	private static final Logger logger = LoggerFactory.getLogger(BookController.class);
	private final Workbook workbook; 
	private final InputStream inputStream; 
	public BookServiceImpl(InputStream inputStream, Workbook workbook) 
	{ 
		this.inputStream = inputStream; 
		this.workbook = workbook; 
	}	
	
	
    @Override
    public String checkProfitability(String isbn) {
        String message = "Needs Rework";
        logger.info("Received ISBN: {}", isbn);
        
        try {
        	FileInputStream file = new FileInputStream(new File("C:\\Users\\HP\\Desktop\\MyFolders\\Dinesh Java Projects\\demo\\isbns.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            logger.debug("Opened Excel file and read the first sheet.");

            for (Row row : sheet) {
                if (row.getCell(0).getStringCellValue().equals(isbn)) {
                    double sellingPrice = row.getCell(1).getNumericCellValue();
                    double purchasePrice = row.getCell(2).getNumericCellValue();
                    double shippingCost = row.getCell(3).getNumericCellValue();
                    double amazonCommission = row.getCell(4).getNumericCellValue();
                    double profitThreshold = purchasePrice + shippingCost + amazonCommission + 0.15 * purchasePrice;
                    logger.info("Selling is profitable for ISBN: {}", isbn); 

                    if (sellingPrice > profitThreshold) {
                        message = "Selling ["+isbn+  "] is profitable";
                        logger.info("Selling Price"+ sellingPrice+ " PurchasePrice"+ purchasePrice+ " shippingCost"+ shippingCost+ " amazonCommission"+ amazonCommission+" profitThreshold" + profitThreshold);
                        workbook.close();
                        return message;
                    }
                    break;
                }
                
            }
            logger.debug("isbn match not found in excel");
            message = "ISBN not found in excel";
            workbook.close();
        } catch (Exception e) {
        	logger.error("Error processing the Excel file", e);
            e.printStackTrace();
        }
        return message;
    }
}

