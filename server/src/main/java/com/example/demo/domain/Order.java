package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by hector on 26/05/2017.
 */

@Document
@Getter
@NoArgsConstructor
public class Order {

    public Order(String name) {
        this.name = name;
    }

    @Id
    private String id;

    private String name;

}

