package org.springframework.shell.samples.helloworld.commands;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import common.OrderBook;
import common.ReadBook;
import common.SearchBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class ClientCommands implements CommandMarker {

    @Autowired
    private ActorRef clientActor;

    @Autowired
    private ActorSystem actorSystem;

    @CliCommand(value = "search", help = "Search a book")
    public void search(@CliOption(key = {"book"}, mandatory = true, help = "Give book title") final String book) {
        actorSystem.actorSelection("akka.tcp://server@127.0.0.1:2599/user/search").tell(new SearchBook(book), clientActor);
    }

    @CliCommand(value = "order", help = "Order a book")
    public void order(@CliOption(key = {"book"}, mandatory = true, help = "Give book title") final String book) {
        actorSystem.actorSelection("akka.tcp://server@127.0.0.1:2599/user/order").tell(new OrderBook(book), clientActor);
    }

    @CliCommand(value = "read", help = "Read a book")
    public void read(@CliOption(key = {"book"}, mandatory = true, help = "Give book title") final String book) {
        actorSystem.actorSelection("akka.tcp://server@127.0.0.1:2599/user/read").tell(new ReadBook(book), clientActor);
    }

}
