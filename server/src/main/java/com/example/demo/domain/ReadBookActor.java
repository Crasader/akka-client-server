package com.example.demo.domain;

import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.stream.ActorMaterializer;
import akka.stream.OverflowStrategy;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import com.example.demo.repository.first.FirstBookRepository;
import common.ReadBook;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by hector on 25/05/2017.
 */
public class ReadBookActor extends AbstractActor {

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .matchAny(o -> SupervisorStrategy.restart())
            .build());
    private final FirstBookRepository bookRepository;

    public ReadBookActor(FirstBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    @Override
    public Receive createReceive() {
        return this.receiveBuilder()
                .match(ReadBook.class, b -> {
                    ActorRef sink = Source.actorRef(1000, OverflowStrategy.dropNew())
                            .throttle(1, Duration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
                            .to(Sink.actorRef(getSender(), NotUsed.getInstance()))
                            .run(ActorMaterializer.create(getContext()));
                    bookRepository
                            .findByName(b.getTitle())
                            .getContentLines()
                            .forEach(l -> sink.tell(l, getSelf()));
                })
                .build();
    }
}
