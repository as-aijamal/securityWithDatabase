package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.entity.Book;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.BookRepository;
import peaksoft.service.BookService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book saveBook(Book book) {
        log.info(book.getName()+" is saved successfully");
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {

        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() ->{
             log.error("Book doesn't exists");
                throw new NotFoundException("Book with id " + id + " doesn't exists!");

         });
    }

    @Transactional
    @Override
    public Book updateBook(Long id, Book book) {
        Book book1 = getBookById(id);
        book1.setName(book.getName());
        book1.setAuthor(book.getAuthor());
        book1.setPrice(book.getPrice());
        log.info("Updated");
        bookRepository.save(book1);

        return book1;
    }

    @Override
    public SimpleResponse deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            log.error("Book doesn't exists");
            throw new NotFoundException("Book with id "+id+" doesn't exists!");
        }
        bookRepository.deleteById(id);
        log.info("Book successfully deleted...");
        return new SimpleResponse(
                HttpStatus.OK,
                "Book with id:"+ id + " deleted"
        );
    }
}
