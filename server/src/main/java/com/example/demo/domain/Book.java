package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by hector on 25/05/2017.
 */

@Document
@Getter
@NoArgsConstructor
public class Book {

    public Book(String name, List<String> content) {
        this.name = name;
        this.contentLines = content;
    }

    @Id
    private String id;

    private String name;

    private List<String> contentLines;
}
