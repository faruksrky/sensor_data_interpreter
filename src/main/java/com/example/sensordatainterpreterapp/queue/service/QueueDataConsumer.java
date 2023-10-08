package com.example.sensordatainterpreterapp.queue.service;
import com.example.sensordatainterpreterapp.enums.PackageStatus;
import com.example.sensordatainterpreterapp.request_response.QueueRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class QueueDataConsumer {
    private final QueueServiceImp queueService;

    /** RabbitMQ Queue yapisi icerisinde yer alan mesajlarin dinlenmesini @RabbitListener saglar.
    Program calistiginda listener devreye girer ve kuyrukta mesaj varsa request olarak alinip database'e gonderilir.*/
    @RabbitListener(queues = "${rabbitmq.queues.sensor-data}")
    public void consumer(QueueRequest queueRequest) {
        log.info("Consumed {} from queue", queueRequest);

        /** Kuyruktan okunan istatiksel türdeki mesajlar veritabanina kayit ediliyor,
         * else de ise operational islemlerden sonra kayit islemi yapilir */
        if(queueRequest.getType() != PackageStatus.OPERATIONAL) {
            queueService.send(queueRequest);
        }
        else {
            queueService.deviceResponse(queueRequest);
        }
    }
}
