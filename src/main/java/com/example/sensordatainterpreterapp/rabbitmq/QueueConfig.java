package com.example.sensordatainterpreterapp.rabbitmq;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class QueueConfig {

    /** application.proporties içerisinden okuyabilmek için @value olarak tanımlanmistir */

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.sensor-data}")
    private String sensorDataQueue;

    /** Gelen mesajın, “routing key”e göre hangi queue’ya “nasıl” gönderileceğini belirtmek için kullanılır **/


    @Value("${rabbitmq.routing-keys.internal-sensorData}")
    private String internalSensorDataRoutingKey;

    /** Verilen yönlendirme anahtarlarına göre farklı kuyruklara yazma işlemleri rabbit exchange ile yapılacak **/
    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    /** Mesajların son olarak düştüğü kuyruk Queue oluşturulmuştur */
    @Bean
    public Queue sensorDataQueue() {
        return new Queue(this.sensorDataQueue);
    }

    /** Data Bind işlemi yapılmaktadır.*/
    @Bean
    public Binding internalToSensorDataBinding() {
        return BindingBuilder
                .bind(sensorDataQueue())
                .to(internalTopicExchange())
                .with(this.internalSensorDataRoutingKey);
    }
}
