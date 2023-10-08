package com.example.sensordatainterpreterapp.request_response;


import com.example.sensordatainterpreterapp.enums.EventTypes;
import com.example.sensordatainterpreterapp.enums.PackageStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record ReceiveDataRegistrationRequest(
        PackageStatus type,
        Float air_pressure,
        Float humidity,
        Long event_id,
        Long deviceId,
        Double device_amount,
        EventTypes event_type,
        String event_type_reason,
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        Date regDate,
        Double eventLocLat,
        Double eventLocLng) {
}
