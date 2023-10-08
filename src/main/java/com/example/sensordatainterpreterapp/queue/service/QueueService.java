package com.example.sensordatainterpreterapp.queue.service;

import com.example.sensordatainterpreterapp.request_response.DeviceLocationResponse;
import com.example.sensordatainterpreterapp.request_response.QueueRequest;
import com.example.sensordatainterpreterapp.request_response.ReceiveDataRegistrationRequest;

import java.util.Date;
import java.util.List;

public interface QueueService {

    void send(QueueRequest request);
    void deviceResponse(QueueRequest queueRequest);
    List<DeviceLocationResponse> deviceLocationResponse (Long deviceId, Date start_date, Date end_date);
    void registerDataToQueue(ReceiveDataRegistrationRequest request);
}
