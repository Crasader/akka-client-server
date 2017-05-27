package com.example.demo.repository.first;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by hector on 25/05/2017.
 */
public interface FirstBookRepository extends MongoRepository<Book, String>, BookRepository {
    Book findByName(String name);
}
