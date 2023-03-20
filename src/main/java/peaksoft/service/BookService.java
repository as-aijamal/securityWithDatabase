package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.entity.Book;

import java.util.List;

public interface BookService {

    Book saveBook (Book book);

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book updateBook(Long id, Book book);

    SimpleResponse deleteBook(Long id);


}

