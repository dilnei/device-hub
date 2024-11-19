package com.device.hub.persistence.repositories;

import com.device.hub.persistence.entities.DeviceEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<DeviceEntity, Long> {

  List<DeviceEntity> findByBrand(String brand);

  boolean existsByName(@NotBlank @NotNull @NotEmpty String name);
}
