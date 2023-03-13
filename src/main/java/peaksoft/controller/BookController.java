package peaksoft.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.entity.Book;
import peaksoft.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor

public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public Book saveBook(@RequestBody Book book){
      return bookService.saveBook(book);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN','VENDOR','CUSTOMER')")
    @PermitAll
    @GetMapping
    public List<Book> getAllBooks(){
       return bookService.getAllBooks();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','VENDOR','CUSTOMER')")
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','VENDOR')")
    @PutMapping("/{id}")
    public Book updateBook(@RequestBody Book book, @PathVariable Long id){
        return bookService.updateBook(id,book);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','VENDOR')")
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id){
       return bookService.deleteBook(id);
    }

}
