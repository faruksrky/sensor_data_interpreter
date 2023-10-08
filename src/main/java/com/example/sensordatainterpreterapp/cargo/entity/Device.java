package com.example.sensordatainterpreterapp.cargo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="tbl_device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "device_id")
    private Long id;
    private String device_name;
    private Integer device_age;
    private Integer device_amount;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column (name = "created_on",columnDefinition = "date") // device'e ait registration zamani
    private Date created_on;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column (name = "last_update", columnDefinition = "date") // device'e ait guncelleme zaman araligi
    private Date last_update;

}
