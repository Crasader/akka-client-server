package org.springframework.shell.samples.helloworld.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by hector on 25/05/2017.
 */

@Configuration
public class ClientConfig {

    @Bean
    public Config config() {
        return ConfigFactory.parseFile(new File("/Users/hector/IdeaProjects/sr-akka/samples/helloworld/src/main/resources/client.conf"));
    }

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("client", config());
    }

    @Bean
    public ActorRef clientActor() {
        return actorSystem().actorOf(Props.create(ClientActor.class));
    }
}
