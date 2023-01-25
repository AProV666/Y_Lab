package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Тестирование функционала {@link com.edu.ulab.app.service.impl.BookServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing book functionality.")
public class BookServiceImplTest {
    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Test
    @DisplayName("Создание книги. Должно пройти успешно.")
    void saveBook_Test() {

        //given
        Person person  = new Person();
        person.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setUserId(1L);
        bookDto.setAuthor("test author");
        bookDto.setTitle("test title");
        bookDto.setPageCount(1000);

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("test author");
        result.setTitle("test title");
        result.setPageCount(1000);

        Book book = new Book();
        book.setPageCount(1000);
        book.setTitle("test title");
        book.setAuthor("test author");
        book.setPerson(person);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setPageCount(1000);
        savedBook.setTitle("test title");
        savedBook.setAuthor("test author");
        savedBook.setPerson(person);

        //when
        when(bookMapper.bookDtoToBook(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);

        //then
        BookDto bookDtoResult = bookService.createBook(bookDto);
        assertEquals(1L, bookDtoResult.getId());
    }


    //update
    @Test
    @DisplayName("Изменение книги. Должно пройти успешно.")
    void updateBook_Test() {

        //given
        Person person  = new Person();
        person.setId(1L);

        BookDto updateBookDto = new BookDto();
        updateBookDto.setId(1L);
        updateBookDto.setUserId(1L);
        updateBookDto.setAuthor("John Ronald Reuel Tolkien");
        updateBookDto.setTitle("The silmarillion");
        updateBookDto.setPageCount(100);

        Book updateBook = new Book();
        updateBook.setId(1L);
        updateBook.setAuthor("John Ronald Reuel Tolkien");
        updateBook.setTitle("The silmarillion");
        updateBook.setPageCount(100);
        updateBook.setPerson(person);

        //when
        when(bookMapper.bookDtoToBook(updateBookDto)).thenReturn(updateBook);
        when(bookRepository.save(updateBook)).thenReturn(updateBook);
        when(bookMapper.bookToBookDto(updateBook)).thenReturn(updateBookDto);

        //then
        BookDto bookDtoResult = bookService.updateBook(updateBookDto);
        assertEquals(1L, bookDtoResult.getId());
        assertEquals("John Ronald Reuel Tolkien", bookDtoResult.getAuthor());
        assertEquals("The silmarillion", bookDtoResult.getTitle());
    }
    // get
    @Test
    @DisplayName("Выдать книги. Должно пройти успешно.")
    void getBook_Test() {

        //given
        Person person  = new Person();
        person.setId(1L);
        Long bookId = 1L;

        BookDto result = new BookDto();
        result.setId(1L);
        result.setUserId(1L);
        result.setAuthor("John Ronald Reuel Tolkien");
        result.setTitle("The silmarillion");
        result.setPageCount(100);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setAuthor("John Ronald Reuel Tolkien");
        savedBook.setTitle("The silmarillion");
        savedBook.setPageCount(100);
        savedBook.setPerson(person);

        //when

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(savedBook));
        when(bookMapper.bookToBookDto(savedBook)).thenReturn(result);

        //then
        BookDto bookDtoResult = bookService.getBookById(bookId);
        assertEquals("John Ronald Reuel Tolkien", bookDtoResult.getAuthor());
        assertEquals("The silmarillion", bookDtoResult.getTitle());
    }

    //get all
    @Test
    @DisplayName("Выдать все книги по id юзера. Должно пройти успешно.")
    void getAllBook_Test() {

        //given
        Person person  = new Person();
        person.setId(1L);
        Long personId = 1L;

        BookDto firstResult = new BookDto();
        firstResult.setId(1L);
        firstResult.setUserId(1L);
        firstResult.setAuthor("John Ronald Reuel Tolkien");
        firstResult.setTitle("The silmarillion");
        firstResult.setPageCount(100);

        Book firstBook = new Book();
        firstBook.setId(1L);
        firstBook.setAuthor("John Ronald Reuel Tolkien");
        firstBook.setTitle("The silmarillion");
        firstBook.setPageCount(100);
        firstBook.setPerson(person);

        BookDto secondResult = new BookDto();
        secondResult.setId(1L);
        secondResult.setUserId(1L);
        secondResult.setAuthor("John Ronald Reuel Tolkien");
        secondResult.setTitle("The silmarillion");
        secondResult.setPageCount(100);

        Book secondBook = new Book();
        secondBook.setId(1L);
        secondBook.setAuthor("John Ronald Reuel Tolkien");
        secondBook.setTitle("The silmarillion");
        secondBook.setPageCount(100);
        secondBook.setPerson(person);

        List<Book> bookList = new ArrayList<>();
        bookList.add(firstBook);
        bookList.add(secondBook);

        //when
        when(bookRepository.findBookByUserId(personId)).thenReturn(bookList);
        when(bookMapper.bookToBookDto(firstBook)).thenReturn(firstResult);
        when(bookMapper.bookToBookDto(secondBook)).thenReturn(secondResult);

        //then
        List<BookDto> bookDtoResultList = bookService.getBookListByUserId(personId);
        assertEquals(bookDtoResultList.size(), 2);
        assertEquals(bookDtoResultList.get(0), firstResult);
    }

    //delete
    @Test
    @DisplayName("Удалить книгу. Должно пройти успешно.")
    void deleteBook_Test() {

        //given
        Long bookId = 1L;

        //when
        doNothing().when(bookRepository).deleteById(bookId);

        //then
        bookService.deleteBookById(bookId);
    }
}
