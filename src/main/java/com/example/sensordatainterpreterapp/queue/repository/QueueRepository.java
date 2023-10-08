package com.example.sensordatainterpreterapp.queue.repository;


import com.example.sensordatainterpreterapp.queue.entity.QueueData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<QueueData, Long> {

    /**
     * Soru 3 --> Verilen 2 tarih arasındaki device'a ait lokasyon bilgilerini donen method
     * @param start_date
     * @param end_date
     * @return Device
     */
    List<QueueData>  findByDeviceIdAndRegDateBetween (Long device_id, Date start_date, Date end_date);
}
