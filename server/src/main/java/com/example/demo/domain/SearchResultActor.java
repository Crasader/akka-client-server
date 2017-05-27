package com.example.demo.domain;

import akka.actor.AbstractActor;

/**
 * Created by hector on 26/05/2017.
 */
public class SearchResultActor extends AbstractActor {
    private SearchResponse previousResponse;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchResponse.class, o -> {
                    if (previousResponse != null) {
                        if (!previousResponse.isFound()) {
                            getSender().tell(o.isFound() ? "Book found" : "Book not found", getSender());
                        }
                    } else {
                        previousResponse = o;
                        if (o.isFound()) {
                            getSender().tell("Book found", getSender());
                        }
                    }
                })
                .build();
    }
}
