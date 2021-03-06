package conclusao.trabalho.java.pos.sistemacompravendaacoes.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {
	
	public static final String QUEUE_MESSAGES = "messages-queue";
    public static final String EXCHANGE_MESSAGES = "messages-exchange";
    public static final String QUEUE_DEAD_MESSAGES = "dead-messages-queue";
    public static final String QUEUE_BUY_MESSAGES = "messages-buy-queue";
    public static final String EXCHANGE_BUY_MESSAGES = "messages-buy-exchange";
    public static final String QUEUE_DEAD_BUY_MESSAGES = "dead-messages-buy-queue";
    public static final String QUEUE_SELL_MESSAGES = "messages-sell-queue";
    public static final String EXCHANGE_SELL_MESSAGES = "messages-sell-exchange";
    public static final String QUEUE_DEAD_SELL_MESSAGES = "dead-messages-sell-queue";
    
 
    @Bean
    Queue messagesQueue() {
    	return QueueBuilder.durable(QUEUE_MESSAGES)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_MESSAGES)
                .withArgument("x-message-ttl", 15000) //if message is not consumed in 15 seconds send to DLQ
                .build();
    }
    
    @Bean
    Queue messagesBuyQueue() {
    	return QueueBuilder.durable(QUEUE_BUY_MESSAGES)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_BUY_MESSAGES)
                .withArgument("x-message-ttl", 60000) //if message is not consumed in 60 seconds send to DLQ
                .build();
    }

    @Bean
    Queue messagesSellQueue() {
    	return QueueBuilder.durable(QUEUE_SELL_MESSAGES)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_SELL_MESSAGES)
                .withArgument("x-message-ttl", 15000) //if message is not consumed in 15 seconds send to DLQ
                .build();
    }
 
    @Bean
    Queue deadMessagesQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_MESSAGES).build();
    }
 
    @Bean
    Exchange messagesExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_MESSAGES).build();
    }
 
    @Bean
    Binding binding(Queue messagesQueue, TopicExchange messagesExchange) {
        return BindingBuilder.bind(messagesQueue).to(messagesExchange).with(QUEUE_MESSAGES);
    }
    
 
    @Bean
    Queue deadMessagesBuyQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_BUY_MESSAGES).build();
    }
 
    @Bean
    Exchange messagesBuyExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_BUY_MESSAGES).build();
    }
    
    @Bean
    Queue deadMessagesSellQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_SELL_MESSAGES).build();
    }
 
    @Bean
    Exchange messagesSellExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_SELL_MESSAGES).build();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

	@Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar register) {
        register.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
	
	@Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
	
	@Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }
	
	@Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

}
