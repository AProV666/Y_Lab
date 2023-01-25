package com.edu.ulab.app.repository;

import com.edu.ulab.app.config.SystemJpaTest;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.Person;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты репозитория {@link BookRepository}.
 */
@SystemJpaTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    //create
    @DisplayName("Сохранить книгу и автора. Число select должно равняться 2")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void findAllBadges_thenAssertDmlCount() {

        //given
        Person person = new Person();
        person.setAge(111);
        person.setTitle("reader");
        person.setFullName("Test Test");

        Person savedPerson = userRepository.save(person);

        Book book = new Book();
        book.setAuthor("Test Author");
        book.setTitle("test");
        book.setPageCount(1000);
        book.setPerson(savedPerson);

        //when
        Book result = bookRepository.save(book);

        //then
        assertThat(result.getPageCount()).isEqualTo(1000);
        assertThat(result.getTitle()).isEqualTo("test");
        assertSelectCount(2);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    //update
    @DisplayName("Обновить книгу. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updateAllBadges_thenAssertDmlCount() {

        //given
        Book updateBook = new Book();
        updateBook.setId(3003L);
        updateBook.setAuthor("John Ronald Reuel Tolkien");
        updateBook.setTitle("The silmarillion");
        updateBook.setPageCount(666);

        //when
        Book result = bookRepository.save(updateBook);

        //then
        assertThat(result.getTitle()).isEqualTo("The silmarillion");
        assertThat(result.getPageCount()).isEqualTo(666);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    //get
    @DisplayName("Выдать книгу. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getBadges_thenAssertDmlCount() {

        //given
        Long bookId = 3003L;

        //when
        Book result = bookRepository.findById(bookId).orElse(new Book());

        //then
        assertThat(result.getPageCount()).isEqualTo(6655);
        assertThat(result.getTitle()).isEqualTo("more default book");
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    //get all
    @DisplayName("Выдать все книги по юзеру. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getAllBadgesByUserId_thenAssertDmlCount() {

        //given
        Long personId = 1001L;

        //when
        List<Book> resultBookList = bookRepository.findBookByUserId(personId);

        //then
        assertThat(resultBookList.size()).isEqualTo(2);
        assertThat(resultBookList.get(0).getId()).isEqualTo(2002);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    //delete
    @DisplayName("Удалить книгу. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void deleteBadges_thenAssertDmlCount() {

        //given
        Long bookId = 2002L;

        //when
        bookRepository.deleteById(bookId);
        Book book = bookRepository.findById(bookId).orElse(new Book());

        //then
        assertThat(book.getTitle()).isEqualTo(null);
        assertThat(book.getId()).isEqualTo(null);
        assertSelectCount(1);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }
}
