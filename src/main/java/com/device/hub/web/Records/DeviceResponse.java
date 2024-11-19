package com.device.hub.web.Records;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Device Response Object")
public record DeviceResponse(
    @Schema(description = "Device ID", example = "1") Long id,
    @Schema(description = "Device Name", example = "Computer") String name,
    @Schema(description = "Device Brand", example = "Apple") String brand,
    @Schema(description = "Datetime when device was registered", example = "2024-11-19 13:21:02") LocalDateTime creationTime) {

}
