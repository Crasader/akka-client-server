package com.example.demo.domain;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import com.example.demo.repository.first.OrderRepository;
import common.OrderBook;
import scala.concurrent.duration.Duration;

/**
 * Created by hector on 25/05/2017.
 */
public class OrderActor extends AbstractActor {

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .matchAny(o -> SupervisorStrategy.escalate())
            .build());

    private final OrderRepository orderRepository;

    public OrderActor(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(OrderBook.class, b -> {
                    orderRepository.save(new Order(b.getTitle()));
                    getContext().actorSelection(getSender().path()).tell("Book ordered", null);
                })
                .build();
    }
}
