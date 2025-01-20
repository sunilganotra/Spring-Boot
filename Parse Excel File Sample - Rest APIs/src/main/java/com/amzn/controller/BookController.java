package com.amzn.controller;
import com.amzn.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public static class ISBNRequest {
        private String isbn;

        // Getters and setters
        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
    }

    @PostMapping("/check-profitability")
    public String checkProfitability(@RequestBody ISBNRequest request) {
        String isbn = request.getIsbn();
        return bookService.checkProfitability(isbn);
    }
}
