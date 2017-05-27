package com.example.demo.config;

import com.example.demo.domain.Book;
import com.example.demo.repository.first.FirstBookRepository;
import com.example.demo.repository.first.OrderRepository;
import com.example.demo.repository.second.SecondBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by hector on 25/05/2017.
 */
@Component
public class DatabaseInitializer implements ApplicationRunner {

    private final static List<String> POTTER_CONTENT = IntStream.range(0, 5)
            .mapToObj(i -> "asdibierbvierubfalwebudlaiwbfasdkfasasudbfaksbfaoisdfaof")
            .collect(Collectors.toList());
    private final FirstBookRepository firstBookRepository;
    private final SecondBookRepository secondBookRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public DatabaseInitializer(FirstBookRepository firstBookRepository, SecondBookRepository secondBookRepository, OrderRepository orderRepository) {
        this.firstBookRepository = firstBookRepository;
        this.secondBookRepository = secondBookRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        firstBookRepository.deleteAll();
        secondBookRepository.deleteAll();
        orderRepository.deleteAll();
        firstBookRepository.save(new Book("Potter", POTTER_CONTENT));
        secondBookRepository.save(new Book("Potter", POTTER_CONTENT));
    }
}
