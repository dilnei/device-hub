package com.device.hub.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.device.hub.mappers.DeviceMapper;
import com.device.hub.persistence.entities.DeviceEntity;
import com.device.hub.persistence.repositories.DeviceRepository;
import com.device.hub.web.Records.DeviceCreateRequest;
import com.device.hub.web.Records.DeviceResponse;
import com.device.hub.web.Records.DeviceUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DeviceServiceTest {

  @Mock
  private DeviceRepository deviceRepository;

  @Mock
  private DeviceMapper deviceMapper;

  @InjectMocks
  private DeviceService deviceService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createDevice_Success() {
    DeviceCreateRequest request = new DeviceCreateRequest("Device1", "Brand1");
    DeviceEntity deviceEntity = new DeviceEntity();
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());

    when(deviceMapper.toDevice(request)).thenReturn(deviceEntity);
    when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
    when(deviceMapper.toDevice(deviceEntity)).thenReturn(response);

    DeviceResponse result = deviceService.createDevice(request);

    assertEquals(response, result);
  }

  @Test
  void getAllDevices_Success() {
    DeviceEntity deviceEntity = new DeviceEntity();
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());
    List<DeviceEntity> deviceEntities = List.of(deviceEntity);
    List<DeviceResponse> responses = List.of(response);

    when(deviceRepository.findAll()).thenReturn(deviceEntities);
    when(deviceMapper.toDevice(deviceEntity)).thenReturn(response);

    List<DeviceResponse> result = deviceService.getAllDevices();

    assertEquals(responses, result);
  }

  @Test
  void getAllDevices_NoContent() {
    when(deviceRepository.findAll()).thenReturn(Collections.emptyList());

    List<DeviceResponse> result = deviceService.getAllDevices();

    assertTrue(result.isEmpty());
  }

  @Test
  void getDeviceById_Success() {
    DeviceEntity deviceEntity = new DeviceEntity();
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());

    when(deviceRepository.findById(1L)).thenReturn(Optional.of(deviceEntity));
    when(deviceMapper.toDevice(deviceEntity)).thenReturn(response);

    Optional<DeviceResponse> result = deviceService.getDeviceById(1L);

    assertTrue(result.isPresent());
    assertEquals(response, result.get());
  }

  @Test
  void getDeviceById_NotFound() {
    when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

    Optional<DeviceResponse> result = deviceService.getDeviceById(1L);

    assertTrue(result.isEmpty());
  }

  @Test
  void updateDevice_Success() {
    DeviceUpdateRequest request = new DeviceUpdateRequest("Device1", "Brand1");
    DeviceEntity deviceEntity = new DeviceEntity();
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());

    when(deviceRepository.findById(1L)).thenReturn(Optional.of(deviceEntity));
    when(deviceRepository.save(deviceEntity)).thenReturn(deviceEntity);
    when(deviceMapper.toDevice(deviceEntity)).thenReturn(response);

    Optional<DeviceResponse> result = deviceService.updateDevice(1L, request);

    assertTrue(result.isPresent());
    assertEquals(response, result.get());
  }

  @Test
  void updateDevice_NotFound() {
    DeviceUpdateRequest request = new DeviceUpdateRequest("Device1", "Brand1");

    when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(EntityNotFoundException.class, () -> deviceService.updateDevice(1L, request));
  }

  @Test
  void updateDevice_InvalidRequest() {
    DeviceUpdateRequest request = new DeviceUpdateRequest(null, null);
    DeviceEntity deviceEntity = new DeviceEntity();

    when(deviceRepository.findById(1L)).thenReturn(Optional.of(deviceEntity));

    assertThrows(IllegalArgumentException.class, () -> deviceService.updateDevice(1L, request));
  }

  @Test
  void deleteDevice_Success() {
    doNothing().when(deviceRepository).deleteById(1L);

    deviceService.deleteDevice(1L);

    verify(deviceRepository, times(1)).deleteById(1L);
  }

  @Test
  void getDevicesByBrand_Success() {
    DeviceEntity deviceEntity = new DeviceEntity();
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());
    List<DeviceEntity> deviceEntities = List.of(deviceEntity);
    List<DeviceResponse> responses = List.of(response);

    when(deviceRepository.findByBrand("Brand1")).thenReturn(deviceEntities);
    when(deviceMapper.toDevices(deviceEntities)).thenReturn(responses);

    List<DeviceResponse> result = deviceService.getDevicesByBrand("Brand1");

    assertEquals(responses, result);
  }

  @Test
  void getDevicesByBrand_NoContent() {
    when(deviceRepository.findByBrand("Brand1")).thenReturn(Collections.emptyList());

    List<DeviceResponse> result = deviceService.getDevicesByBrand("Brand1");

    assertTrue(result.isEmpty());
  }
}