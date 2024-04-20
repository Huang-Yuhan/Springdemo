package com.example.springdemo;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdemoApplication.class, args);
    }

//    @Bean
//    public NewTopic takeOrderTopic() {
//        return TopicBuilder.name("takeOrder")
//                .partitions(10)
//                .replicas(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic takeOrderResultTopic(){
//        return TopicBuilder.name("takeOrderResult")
//                .partitions(10)
//                .replicas(3)
//                .build();
//    }

}
