package com.device.hub.services;

import com.device.hub.mappers.DeviceMapper;
import com.device.hub.persistence.entities.DeviceEntity;
import com.device.hub.persistence.repositories.DeviceRepository;
import com.device.hub.web.Records.DeviceCreateRequest;
import com.device.hub.web.Records.DeviceResponse;
import com.device.hub.web.Records.DeviceUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

  private final DeviceRepository deviceRepository;
  private final DeviceMapper deviceMapper;


  @Autowired
  public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
    this.deviceRepository = deviceRepository;
    this.deviceMapper = deviceMapper;
  }

  public DeviceResponse createDevice(DeviceCreateRequest deviceCreateRequest) {
    DeviceEntity device = deviceMapper.toDevice(deviceCreateRequest);
    device = deviceRepository.save(device);
    return deviceMapper.toDevice(device);
  }

  public List<DeviceResponse> getAllDevices() {
    return deviceRepository.findAll()
        .stream()
        .map(deviceMapper::toDevice)
        .toList();
  }

  public Optional<DeviceResponse> getDeviceById(Long id) {
    return deviceRepository.findById(id)
        .map(deviceMapper::toDevice);
  }

  public Optional<DeviceResponse> updateDevice(Long id, DeviceUpdateRequest updateRequest) {

    DeviceEntity existingDevice = deviceRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Device not found"));

    if (updateRequest.name() == null &&
        updateRequest.brand() == null) {
      throw new IllegalArgumentException("At least one field must be provided for an update");
    }

    Optional.ofNullable(updateRequest.name())
        .ifPresent(existingDevice::setName);

    Optional.ofNullable(updateRequest.brand())
        .ifPresent(existingDevice::setBrand);

    DeviceEntity savedDevice = deviceRepository.save(existingDevice);

    return Optional.of(deviceMapper.toDevice(savedDevice));
  }

  public void deleteDevice(Long id) {
    deviceRepository.deleteById(id);
  }

  public List<DeviceResponse> getDevicesByBrand(String brand) {
    List<DeviceEntity> deviceEntities = deviceRepository.findByBrand(brand);
    if (deviceEntities.isEmpty()) {
      return Collections.emptyList();
    } else {
      return deviceMapper.toDevices(deviceEntities);
    }
  }

}
