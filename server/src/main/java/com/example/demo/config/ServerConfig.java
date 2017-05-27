package com.example.demo.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.example.demo.domain.OrderActor;
import com.example.demo.domain.ReadBookActor;
import com.example.demo.domain.SearchActor;
import com.example.demo.repository.first.FirstBookRepository;
import com.example.demo.repository.first.OrderRepository;
import com.example.demo.repository.second.SecondBookRepository;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.File;

/**
 * Created by hector on 25/05/2017.
 */

@Configuration
public class ServerConfig {

    @Bean
    public Config config() {
        return ConfigFactory.parseFile(new File("/Users/hector/Downloads/akka/akka-server/src/main/resources/server.conf"));
    }

    @Bean
    public ActorSystem actorSystem() {
        return ActorSystem.create("server", config());
    }

    @Bean
    ActorRef searchActor(FirstBookRepository firstBookRepository, SecondBookRepository secondBookRepository) {
        return actorSystem().actorOf(Props.create(SearchActor.class, firstBookRepository, secondBookRepository), "search");
    }

    @Bean
    ActorRef readBookActor(FirstBookRepository firstBookRepository) {
        return actorSystem().actorOf(Props.create(ReadBookActor.class, firstBookRepository), "read");
    }

    @Bean
    ActorRef orderBookActor(OrderRepository orderRepository) {
        return actorSystem().actorOf(Props.create(OrderActor.class, orderRepository), "order");
    }

    @Configuration
    @EnableMongoRepositories(basePackages = "com.example.demo.repository.first", mongoTemplateRef = "mongoTemplate")
    public class MainMongoConfig {
        @Bean
        public Mongo firstMongo() throws Exception {
            return new MongoClient("localhost");
        }

        @Bean
        public MongoTemplate mongoTemplate() throws Exception {
            return new MongoTemplate(firstMongo(), "test1");
        }
    }

    @Configuration
    @EnableMongoRepositories(basePackages = "com.example.demo.repository.second", mongoTemplateRef = "secondMongoTemplate")
    public class SecondaryMongoConfig {
        @Bean
        public Mongo secondMongo() throws Exception {
            return new MongoClient("localhost");
        }

        @Bean
        public MongoTemplate secondMongoTemplate() throws Exception {
            return new MongoTemplate(secondMongo(), "test2");
        }
    }
}
