package com.fitness.aiservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    //RabbitMQ setup: DLQ (Dead Letter Queue) = safety net for async systems
    //Queue / Exchange / Binding
    @Bean
    public Queue activityQueue() {
        return new Queue(queue, true);
    }

    //Durable queue
    @Bean
    public DirectExchange activityExchange() {
        return new DirectExchange(exchange);
    }

    //for routing-key–based messaging
    @Bean
    public Binding activityBinding(Queue activityQueue, DirectExchange activityExchange) {
        return BindingBuilder.bind(activityQueue).to(activityExchange).with(routingKey);
    }

    //JSON converter
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //By default, Spring RabbitMQ does this:
    //If a listener throws an exception → requeue the message forever
    //Add a listener container factory— Stop infinite requeue
    //NPE thrown
    //Message requeued
    //Infinite loop
    //IntelliJ lagged like crazy
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);

        // THIS LINE SAVES LAPTOPS
        factory.setDefaultRequeueRejected(false);

        return factory;
    }
}