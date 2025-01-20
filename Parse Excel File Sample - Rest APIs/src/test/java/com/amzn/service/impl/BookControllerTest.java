package com.amzn.service.impl;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.amzn.controller.BookController;
import com.amzn.service.BookService;

@WebMvcTest(BookController.class)
@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void testCheckProfitability() throws Exception {
        String isbn = "1234567890";
        when(bookService.checkProfitability(isbn)).thenReturn("Selling [1234567890] is profitable");

        mockMvc.perform(post("/api/checkProfitability")
                .contentType("application/json")
                .content("{\"isbn\":\"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Selling [1234567890] is profitable"));
    }
}
