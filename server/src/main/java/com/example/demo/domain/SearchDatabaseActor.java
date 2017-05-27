package com.example.demo.domain;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import com.example.demo.repository.BookRepository;
import common.SearchBook;
import scala.concurrent.duration.Duration;

/**
 * Created by hector on 25/05/2017.
 */
public class SearchDatabaseActor extends AbstractActor {

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .matchAny(o -> SupervisorStrategy.stop())
            .build());
    private final BookRepository bookRepository;

    private final ActorRef responseActor;

    public SearchDatabaseActor(BookRepository bookRepository, ActorRef responseActor) {
        this.bookRepository = bookRepository;
        this.responseActor = responseActor;
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(SearchBook.class, b -> {
                    responseActor.forward(new SearchResponse(bookExists(b)), context());
                })
                .build();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    private boolean bookExists(SearchBook book) {
        return bookRepository.findByName(book.getTitle()) != null;
    }

}
