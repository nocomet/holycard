package com.nocomet.holycard.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/{name}")
    public BookDto.Response save(@PathVariable String name, @RequestBody BookDto.Request request) {
        Book book = bookService.saveBook(name, request.getAuthor());

        return new BookDto.Response(book.getBookId(),
            book.getName(),
            book.getAuthor());

    }

    @GetMapping("/{id}")
    public BookDto.Response save(@PathVariable Long id) {
        Book book = bookService.getBook(id);

        return new BookDto.Response(book.getBookId(),
            book.getName(),
            book.getAuthor());
    }
}
