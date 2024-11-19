package com.device.hub.web.Records;

import java.time.LocalDateTime;

public record DeviceResponse(Long id, String name, String brand, LocalDateTime creationTime) {

}
