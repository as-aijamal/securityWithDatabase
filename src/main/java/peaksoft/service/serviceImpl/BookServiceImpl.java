package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Book;
import peaksoft.repository.BookRepository;
import peaksoft.service.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Book updateBook(Long id, Book book) {
        Book book1=getBookById(id);
        book1.setName(book.getName());
        book1.setAuthor(book.getAuthor());
        book1.setPrice(book.getPrice());
        bookRepository.save(book1);
        return book1;
    }

    @Override
    public String deleteBook(Long id) {
         bookRepository.deleteById(id);
         return "Book with id: "+id+" deleted";
    }
}
