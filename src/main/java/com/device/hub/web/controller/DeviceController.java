package com.device.hub.web.controller;

import static com.device.hub.web.EndpointConstants.API;
import static com.device.hub.web.EndpointConstants.PATH_DEVICES;

import com.device.hub.services.DeviceService;
import com.device.hub.web.Records.DeviceCreateRequest;
import com.device.hub.web.Records.DeviceResponse;
import com.device.hub.web.Records.DeviceUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API)
public class DeviceController {

  private final DeviceService deviceService;

  @Autowired
  public DeviceController(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @PostMapping(PATH_DEVICES)
  public ResponseEntity<DeviceResponse> createDevice(
      @Valid @RequestBody DeviceCreateRequest deviceCreateRequest) {
    DeviceResponse createdDevice = deviceService.createDevice(deviceCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdDevice);
  }

  @GetMapping(PATH_DEVICES)
  public ResponseEntity<List<DeviceResponse>> getAllDevices() {
    List<DeviceResponse> devices = deviceService.getAllDevices();
    return ResponseEntity.ok(devices);
  }

  @GetMapping(PATH_DEVICES + "/{id}")
  public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable Long id) {
    return deviceService.getDeviceById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PutMapping(PATH_DEVICES + "/{id}")
  public ResponseEntity<DeviceResponse> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceUpdateRequest deviceUpdateRequest) {
    return deviceService.updateDevice(id, deviceUpdateRequest)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping(PATH_DEVICES + "/{id}")
  public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
    deviceService.deleteDevice(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(PATH_DEVICES + "/brand/{brand}")
  public ResponseEntity<List<DeviceResponse>> getDevicesByBrand(@PathVariable String brand) {
    List<DeviceResponse> devices = deviceService.getDevicesByBrand(brand);
    if (devices.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(devices);
  }

}
