package com.amzn.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.amzn.service.BookService;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private InputStream inputStream;

    @Mock
    private Workbook workbook;

    @Mock
    private Sheet sheet;

    @Mock
    private Row row;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() throws Exception {
        when(workbook.getSheetAt(0)).thenReturn(sheet);
    }
    
    
    
    @Test
    void testCheckProfitability_Profitable() {
        // Arrange
        String isbn = "1234567890";
        System.out.println("testCheckProfitability_Profitable: isbn Initialization");
        System.out.println("    private InputStreaminputStream;\r\n" + inputStream
        		+ "\r\n"
        		+ "    private Workbook \r\n" + workbook
        		+ "\r\n"
        		+ "    Sheet sheet;\r\n" + sheet
        		+ "\r\n"
        		+ "    private Row ;\r\n" + row);
        when(sheet.iterator()).thenReturn(Collections.singletonList(row).iterator());
        when(row.getCell(0).getStringCellValue()).thenReturn(isbn);
        when(row.getCell(1).getNumericCellValue()).thenReturn(150.0);
        when(row.getCell(2).getNumericCellValue()).thenReturn(100.0);
        when(row.getCell(3).getNumericCellValue()).thenReturn(20.0);
        when(row.getCell(4).getNumericCellValue()).thenReturn(15.0);

        // Act
        String result = bookService.checkProfitability(isbn);

        // Assert
        assertEquals("Selling [1234567890] is profitable", result);
    }

    @Test
    void testCheckProfitability_NotFound() {
        // Arrange
        String isbn = "1234567890";
        when(sheet.iterator()).thenReturn(Collections.singletonList(row).iterator());
        when(row.getCell(0).getStringCellValue()).thenReturn("9876543210");

        // Act
        String result = bookService.checkProfitability(isbn);

        // Assert
        assertEquals("ISBN not found in excel", result);
    }

    @Test
    void testCheckProfitability_NeedsRework() {
        // Arrange
        String isbn = "1234567890";
        when(sheet.iterator()).thenReturn(Collections.singletonList(row).iterator());
        when(row.getCell(0).getStringCellValue()).thenReturn(isbn);
        when(row.getCell(1).getNumericCellValue()).thenReturn(100.0);
        when(row.getCell(2).getNumericCellValue()).thenReturn(100.0);
        when(row.getCell(3).getNumericCellValue()).thenReturn(20.0);
        when(row.getCell(4).getNumericCellValue()).thenReturn(15.0);

        // Act
        String result = bookService.checkProfitability(isbn);

        // Assert
        assertEquals("Needs Rework", result);
    }
}
