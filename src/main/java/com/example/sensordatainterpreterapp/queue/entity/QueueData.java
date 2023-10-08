package com.example.sensordatainterpreterapp.queue.entity;
import com.example.sensordatainterpreterapp.enums.EventTypes;
import com.example.sensordatainterpreterapp.enums.PackageStatus;
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
@Table (name="tbl_queue_data")
public class QueueData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*Queue datalarının tipini enum uzerinden alacagiz.*/
    @Enumerated(EnumType.ORDINAL)
    private PackageStatus type;
    private Float air_pressure;
    private Float humidity;

    @Column (name = "event_id")
    private Long event_id;
    @Column (name = "device_id")
    private Long deviceId;
    @Enumerated(EnumType.ORDINAL)
    @Column (name = "event_type")
    private EventTypes event_type;

    @Column (name = "event_type_reason")
    private String event_type_reason;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column (name = "reg_date",columnDefinition = "date") // event'e ait registration zamani
    private Date regDate;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column (name = "update",columnDefinition = "date") // event'e ait guncelleme zaman araligi
    private Date last_update;

    private Double eventLocLat;
    private Double eventLocLng;
    private Double device_amount;



}
