package com.example.demo.repository.first;

import com.example.demo.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by hector on 26/05/2017.
 */
public interface OrderRepository extends MongoRepository<Order, String> {
}
