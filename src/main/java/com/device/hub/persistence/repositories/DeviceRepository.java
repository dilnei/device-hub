package com.device.hub.persistence.repositories;

import com.device.hub.persistence.entities.DeviceEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

  List<DeviceEntity> findByBrand(String brand);
}
