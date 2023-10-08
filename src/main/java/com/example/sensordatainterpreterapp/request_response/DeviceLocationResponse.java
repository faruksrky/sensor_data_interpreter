package com.example.sensordatainterpreterapp.request_response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceLocationResponse {
    private Long deviceId;
    private Double eventLocLat;
    private Double eventLocLng;
}
