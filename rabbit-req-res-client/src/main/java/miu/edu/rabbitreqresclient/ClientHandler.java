package miu.edu.rabbitreqresclient;

import miu.edu.rabbitreqresclient.service.SimpleService;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.remoting.client.AmqpProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class ClientHandler {

    @Bean
    Queue queue() {
        return new Queue("demoRpcQueue");
    }

    @Bean
    AmqpProxyFactoryBean amqpFactoryBean(AmqpTemplate amqpTemplate) {
        AmqpProxyFactoryBean factoryBean = new AmqpProxyFactoryBean();
        factoryBean.setServiceInterface(SimpleService.class);
        factoryBean.setAmqpTemplate(amqpTemplate);
        return factoryBean;
    }

    @Bean Exchange directExchange(Queue someQueue) {
        DirectExchange exchange = new DirectExchange("rpc_exchange");
        BindingBuilder.bind(someQueue).to(exchange).with("rpc_exchange_key");
        return exchange;
    }

    @Bean RabbitTemplate amqpTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setRoutingKey("rpc_exchange_key");
        template.setExchange("rpc_exchange");
        return template;
    }

}
