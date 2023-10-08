package com.example.sensordatainterpreterapp.cargo.repository;


import com.example.sensordatainterpreterapp.cargo.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {


}
