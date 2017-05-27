package com.example.demo.domain;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import com.example.demo.repository.first.FirstBookRepository;
import com.example.demo.repository.second.SecondBookRepository;
import common.SearchBook;
import scala.concurrent.duration.Duration;

/**
 * Created by hector on 25/05/2017.
 */
public class SearchActor extends AbstractActor {

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .matchAny(o -> SupervisorStrategy.restart())
            .build());

    private final FirstBookRepository firstBookRepository;
    private final SecondBookRepository secondBookRepository;

    public SearchActor(FirstBookRepository firstBookRepository, SecondBookRepository secondBookRepository) {
        this.firstBookRepository = firstBookRepository;
        this.secondBookRepository = secondBookRepository;
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(SearchBook.class, b -> {
                    final ActorRef responseActor =
                            context().actorOf(Props.create(SearchResultActor.class));
                    context().actorOf(Props.create(SearchDatabaseActor.class, firstBookRepository, responseActor))
                            .forward(b, context());
                    context().actorOf(Props.create(SearchDatabaseActor.class, secondBookRepository, responseActor))
                            .forward(b, context());
                })
                .build();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }


    @Override
    public void preStart() throws Exception {

    }
}
