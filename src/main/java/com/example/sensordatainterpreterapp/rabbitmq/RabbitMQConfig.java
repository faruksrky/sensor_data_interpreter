package com.example.sensordatainterpreterapp.rabbitmq;

import lombok.Data;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory;

    @Bean
    public MessageConverter jacksonConverter(){
        return new Jackson2JsonMessageConverter();
    }

    /** Gelen sensor datalarini queue yapısına yazmak için (advance message queue protokol) amqb template kullandik.**/
    @Bean
    public AmqpTemplate amqpTemplate (){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        /** sensor datasi exchange uzerinden object olarak queue icerisine atılmaktadir.
         *  Bu datalari jaksonConverter kutuphanesi ile RabbitMQ icerisibe json olarak insert ediyoruz.
         */

        rabbitTemplate.setMessageConverter(jacksonConverter());
        return rabbitTemplate;
    }

    /** RabbitMQ uzerinden datalari almak icin Queue dinleyen bir Listener tasarımı yapiyoruz.**/
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonConverter());
        return factory;
    }
}
