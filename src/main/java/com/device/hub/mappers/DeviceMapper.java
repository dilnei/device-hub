package com.device.hub.mappers;

import com.device.hub.persistence.entities.DeviceEntity;
import com.device.hub.web.Records.DeviceCreateRequest;
import com.device.hub.web.Records.DeviceResponse;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeviceMapper {

  DeviceEntity toDevice(DeviceCreateRequest deviceCreateRequest);

  DeviceResponse toDevice(DeviceEntity deviceEntity);

  List<DeviceResponse> toDevices(List<DeviceEntity> deviceEntities);
}
