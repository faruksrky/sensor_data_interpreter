package com.example.sensordatainterpreterapp.request_response;

import com.example.sensordatainterpreterapp.enums.EventTypes;
import com.example.sensordatainterpreterapp.enums.PackageStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QueueRequest{
        private PackageStatus type;
        private Float air_pressure;
        private Float humidity;
        private Long event_id;
        private Long deviceId;
        private Double device_amount;
        private EventTypes event_type;
        private String event_type_reason;
        @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
        private Date regDate;
        private Double eventLocLat;
        private Double eventLocLng;
        private String message;
}