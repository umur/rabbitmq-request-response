package miu.edu.rabbitrequestresponse;

import miu.edu.rabbitrequestresponse.service.SimpleService;
import miu.edu.rabbitrequestresponse.service.SimpleServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.remoting.service.AmqpInvokerServiceExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerHandler {

    @Bean
    SimpleService simpleService() {
        return new SimpleServiceImpl();
    }

    @Bean
    Queue queue() {
        return new Queue("demoRpcQueue");
    }

    @Bean
    AmqpInvokerServiceExporter exporter(SimpleService simpleService, AmqpTemplate template) {
        AmqpInvokerServiceExporter exporter = new AmqpInvokerServiceExporter();
        exporter.setServiceInterface(SimpleService.class);
        exporter.setService(simpleService);
        exporter.setAmqpTemplate(template);
        return exporter;
    }

    @Bean
    SimpleMessageListenerContainer listener(ConnectionFactory factory, AmqpInvokerServiceExporter exporter, Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.setMessageListener(exporter);
        container.setQueueNames(queue.getName());
        return container;
    }


}
