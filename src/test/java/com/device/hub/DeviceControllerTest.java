package com.device.hub;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.device.hub.services.DeviceService;
import com.device.hub.web.Records.DeviceCreateRequest;
import com.device.hub.web.Records.DeviceResponse;
import com.device.hub.web.Records.DeviceUpdateRequest;
import com.device.hub.web.controller.DeviceController;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class DeviceControllerTest {

  private MockMvc mockMvc;

  @Mock
  private DeviceService deviceService;

  @InjectMocks
  private DeviceController deviceController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
  }

  @Test
  void createDevice_Success() throws Exception {
    DeviceCreateRequest request = new DeviceCreateRequest("Device1", "Brand1");
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());

    when(deviceService.createDevice(request)).thenReturn(response);

    mockMvc.perform(post("/api/v1/devices")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Device1\",\"brand\":\"Brand1\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Device1"))
        .andExpect(jsonPath("$.brand").value("Brand1"));
  }

  @Test
  void getAllDevices_Success() throws Exception {
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());
    List<DeviceResponse> responses = List.of(response);

    when(deviceService.getAllDevices()).thenReturn(responses);

    mockMvc.perform(get("/api/v1/devices"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Device1"))
        .andExpect(jsonPath("$[0].brand").value("Brand1"));
  }

  @Test
  void getAllDevices_NoContent() throws Exception {
    when(deviceService.getAllDevices()).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/v1/devices"))
        .andExpect(status().isOk());
  }

  @Test
  void getDeviceById_Success() throws Exception {
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());

    when(deviceService.getDeviceById(1L)).thenReturn(Optional.of(response));

    mockMvc.perform(get("/api/v1/devices/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Device1"))
        .andExpect(jsonPath("$.brand").value("Brand1"));
  }

  @Test
  void getDeviceById_NotFound() throws Exception {
    when(deviceService.getDeviceById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/api/v1/devices/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateDevice_Success() throws Exception {
    DeviceUpdateRequest request = new DeviceUpdateRequest("Device1", "Brand1");
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());

    when(deviceService.updateDevice(1L, request)).thenReturn(Optional.of(response));

    mockMvc.perform(put("/api/v1/devices/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Device1\",\"brand\":\"Brand1\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.name").value("Device1"))
        .andExpect(jsonPath("$.brand").value("Brand1"));
  }

  @Test
  void updateDevice_NotFound() throws Exception {
    DeviceUpdateRequest request = new DeviceUpdateRequest("Device1", "Brand1");

    when(deviceService.updateDevice(1L, request)).thenReturn(Optional.empty());

    mockMvc.perform(put("/api/v1/devices/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"Device1\",\"brand\":\"Brand1\"}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteDevice_Success() throws Exception {
    mockMvc.perform(delete("/api/v1/devices/1"))
        .andExpect(status().isNoContent());
  }

  @Test
  void getDevicesByBrand_Success() throws Exception {
    DeviceResponse response = new DeviceResponse(1L, "Device1", "Brand1", LocalDateTime.now());
    List<DeviceResponse> responses = List.of(response);

    when(deviceService.getDevicesByBrand("Brand1")).thenReturn(responses);

    mockMvc.perform(get("/api/v1/devices/brand/Brand1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Device1"))
        .andExpect(jsonPath("$[0].brand").value("Brand1"));
  }

  @Test
  void getDevicesByBrand_NoContent() throws Exception {
    when(deviceService.getDevicesByBrand("Brand1")).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/api/v1/devices/brand/Brand1"))
        .andExpect(status().isNoContent());
  }
}