package com.example.sensordatainterpreterapp.cargo.controller;

import com.example.sensordatainterpreterapp.cargo.entity.Device;
import com.example.sensordatainterpreterapp.cargo.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceRepository deviceRepository;

    @PostMapping("api/v1/device")
    public Device saveAccount(@RequestBody Device device){
        return deviceRepository.save(device);
    }

    @GetMapping("api/v1/devices")
    public List<Device> getDeviceList(){
        return deviceRepository.findAll();
    }

}
