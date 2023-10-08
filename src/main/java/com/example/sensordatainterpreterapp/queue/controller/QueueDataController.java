package com.example.sensordatainterpreterapp.queue.controller;

import com.example.sensordatainterpreterapp.queue.service.QueueService;
import com.example.sensordatainterpreterapp.request_response.DeviceLocationResponse;
import com.example.sensordatainterpreterapp.request_response.ReceiveDataRegistrationRequest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class QueueDataController {

    private QueueService queueService;

    /** Görevleri yapabilmek için kuyruğa veri atmayı sağlayan controller methodu.  **/
    @PostMapping("InsertDataToQueue")
    public void registerData(@RequestBody ReceiveDataRegistrationRequest receiveDataRegistrationRequest) {
        log.info("new sensor data registration {}", receiveDataRegistrationRequest);
        queueService.registerDataToQueue(receiveDataRegistrationRequest);
    }

    /** Görev 3: Kaydedilen istatiksel verilerden device location ları dönen bir servisin controller methodu  **/
    @GetMapping("deviceLocation")
    public List<DeviceLocationResponse> getDeviceLocation(@RequestParam Long deviceId,
                                                          @RequestParam ("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,
                                                                  pattern = "yyyy-MM-dd") Date start_date,
                                                          @RequestParam ("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME,
                                                                  pattern = "yyyy-MM-dd") Date end_date) {
        return queueService.deviceLocationResponse(deviceId,start_date,end_date);
    }
}
