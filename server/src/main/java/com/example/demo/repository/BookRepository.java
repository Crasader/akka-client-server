package com.example.demo.repository;

import com.example.demo.domain.Book;

/**
 * Created by hector on 26/05/2017.
 */
public interface BookRepository {
    Book findByName(String name);
}
