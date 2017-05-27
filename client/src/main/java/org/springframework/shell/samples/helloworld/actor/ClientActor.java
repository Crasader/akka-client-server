package org.springframework.shell.samples.helloworld.actor;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

/**
 * Created by hector on 24/05/2017.
 */

public class ClientActor extends AbstractActor {

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .matchAny(o -> SupervisorStrategy.resume())
            .build());

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .matchAny(System.out::println)
                .build();
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
