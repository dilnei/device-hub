package com.device.hub.web.controller;

import static com.device.hub.web.EndpointConstants.API;
import static com.device.hub.web.EndpointConstants.PATH_DEVICES;

import com.device.hub.services.DeviceService;
import com.device.hub.web.Records.DeviceCreateRequest;
import com.device.hub.web.Records.DeviceResponse;
import com.device.hub.web.Records.DeviceUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(summary = "Create a new device", description = "Adds a new device to the system.")
  @ApiResponse(responseCode = "201", description = "Device created successfully", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
  @ApiResponse(responseCode = "400", description = "Invalid request")
  @PostMapping(PATH_DEVICES)
  public ResponseEntity<DeviceResponse> createDevice(
      @Valid @RequestBody DeviceCreateRequest deviceCreateRequest) {
    DeviceResponse createdDevice = deviceService.createDevice(deviceCreateRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdDevice);
  }

  @Operation(summary = "Retrieve all devices", description = "Fetches a list of all devices.")
  @ApiResponse(responseCode = "200", description = "List of devices retrieved successfully", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
  @ApiResponse(responseCode = "204", description = "No devices found")
  @GetMapping(PATH_DEVICES)
  public ResponseEntity<List<DeviceResponse>> getAllDevices() {
    List<DeviceResponse> devices = deviceService.getAllDevices();
    return ResponseEntity.ok(devices);
  }

  @Operation(summary = "Get a device by ID", description = "Fetches details of a specific device by its ID.")
  @ApiResponse(responseCode = "200", description = "Device retrieved successfully", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
  @ApiResponse(responseCode = "404", description = "Device not found")
  @GetMapping(PATH_DEVICES + "/{id}")
  public ResponseEntity<DeviceResponse> getDeviceById(
      @Parameter(description = "ID of the device to retrieve", required = true) @PathVariable Long id) {
    return deviceService.getDeviceById(id).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Update a device", description = "Updates the details of an existing device.")
  @ApiResponse(responseCode = "200", description = "Device updated successfully", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
  @ApiResponse(responseCode = "404", description = "Device not found")
  @ApiResponse(responseCode = "400", description = "Invalid request")
  @PutMapping(PATH_DEVICES + "/{id}")
  public ResponseEntity<DeviceResponse> updateDevice(
      @Parameter(description = "ID of the device to update", required = true) @PathVariable Long id,
      @Valid @RequestBody DeviceUpdateRequest deviceUpdateRequest) {
    return deviceService.updateDevice(id, deviceUpdateRequest).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Delete a device", description = "Removes a device from the system.")
  @ApiResponse(responseCode = "204", description = "Device deleted successfully")
  @ApiResponse(responseCode = "404", description = "Device not found")
  @DeleteMapping(PATH_DEVICES + "/{id}")
  public ResponseEntity<Void> deleteDevice(
      @Parameter(description = "ID of the device to delete", required = true) @PathVariable Long id) {
    deviceService.deleteDevice(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Retrieve devices by brand", description = "Fetches a list of devices by their brand.")
  @ApiResponse(responseCode = "200", description = "List of devices retrieved successfully", content = @Content(schema = @Schema(implementation = DeviceResponse.class)))
  @ApiResponse(responseCode = "204", description = "No devices found")
  @GetMapping(PATH_DEVICES + "/brand/{brand}")
  public ResponseEntity<List<DeviceResponse>> getDevicesByBrand(
      @Parameter(description = "Brand name to search for devices", required = true) @PathVariable String brand) {
    List<DeviceResponse> devices = deviceService.getDevicesByBrand(brand);
    if (devices.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(devices);
  }

}
