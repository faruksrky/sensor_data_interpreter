package com.example.sensordatainterpreterapp.queue.service;

import com.example.sensordatainterpreterapp.cargo.entity.Device;
import com.example.sensordatainterpreterapp.cargo.repository.DeviceRepository;
import com.example.sensordatainterpreterapp.queue.entity.QueueData;
import com.example.sensordatainterpreterapp.rabbitmq.RabbitMQMessageProducer;
import com.example.sensordatainterpreterapp.queue.repository.QueueRepository;
import com.example.sensordatainterpreterapp.request_response.DeviceLocationResponse;
import com.example.sensordatainterpreterapp.request_response.QueueRequest;
import com.example.sensordatainterpreterapp.request_response.ReceiveDataRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueServiceImp implements QueueService {

    private final QueueRepository queueRepository;
    private final DeviceRepository deviceRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final ModelMapper modelMapper;

    /** Kuyruktan gelen mesajın Queue veritabanına kayıt edilmesi için kullanılan method */
    @Override
    public void send(QueueRequest request) {
        queueRepository.save(
                QueueData.builder()
                        .type(request.getType())
                        .air_pressure(request.getAir_pressure())
                        .humidity(request.getHumidity())
                        .event_id(request.getEvent_id())
                        .deviceId(request.getDeviceId())
                        .device_amount(request.getDevice_amount())
                        .event_type(request.getEvent_type())
                        .event_type_reason(request.getEvent_type_reason())
                        .regDate(request.getRegDate())
                        .eventLocLat(request.getEventLocLat())
                        .eventLocLng(request.getEventLocLng())
                        .build());
    }

    /** Görev 2 icin uygulanan method */
    @Override
    public void deviceResponse(QueueRequest queueRequest){
        /** 2.1 --> Kuyruktan okunan deviceId ile queue datasından farklı bir veritabanından sorgu yapilmaktadir */
            Optional<Device> getDevice = deviceRepository.findById(queueRequest.getDeviceId());

        /** 2.2 --> Veritabanından dönen device objecti icerisinde yer alan device_amount ile
         * kuyruktan okunan airPressure'in yuzdesel olarak %1 etkisi amount'a eklenmistir.*/
        queueRequest.setDevice_amount(getDevice.get().getDevice_amount().doubleValue() + queueRequest.getAir_pressure() % 1);

        /** 2.3 --> 2.2 de elde edilen deger ile kuyruktan okunan mesaj veritabanina kayit ediliyor*/
        send(queueRequest);
    }

    /** Görev 3 icin uygulanan method */
    @Override
    public List<DeviceLocationResponse> deviceLocationResponse (Long deviceId, Date start_date, Date end_date){
        List<QueueData> queueDataList = queueRepository.findByDeviceIdAndRegDateBetween(deviceId,start_date,end_date);
        return modelMapper.map(queueDataList, new TypeToken<List<DeviceLocationResponse>>(){}.getType());
    }
    /** Kuyruka data atmak için kullanılan method  */
    @Override
    public void registerDataToQueue(ReceiveDataRegistrationRequest request) {
        QueueData dataToQueue = QueueData.builder()
                .type(request.type())
                .air_pressure(request.air_pressure())
                .humidity(request.humidity())
                .event_id(request.event_id())
                .deviceId(request.deviceId())
                .device_amount(request.device_amount())
                .event_type(request.event_type())
                .event_type_reason(request.event_type_reason())
                .regDate(request.regDate())
                .eventLocLat(request.eventLocLat())
                .eventLocLng(request.eventLocLng()).build();
        /** Queue request, API'den gonderilen datalari RabbitMQ icerisine publish etmek icin doldurulmustur. */
        QueueRequest queueRequest = new QueueRequest(
                dataToQueue.getType(),
                dataToQueue.getAir_pressure(),
                dataToQueue.getHumidity(),
                dataToQueue.getEvent_id(),
                dataToQueue.getDeviceId(),
                dataToQueue.getDevice_amount(),
                dataToQueue.getEvent_type(),
                dataToQueue.getEvent_type_reason(),
                dataToQueue.getRegDate(),
                dataToQueue.getEventLocLat(),
                dataToQueue.getEventLocLng(),
                String.format("Hi %s, welcome to SensorDataInterpreter...", dataToQueue.getEvent_id())
        );
        /** messageProducer, queue icerisine yukarıda doldurulan request'i, exchange ve routing key'i gondermektedir. */
        rabbitMQMessageProducer.publish(
                queueRequest,
                "internal.exchange",
                "internal.sensorData.routing-key"
        );
    }
}
