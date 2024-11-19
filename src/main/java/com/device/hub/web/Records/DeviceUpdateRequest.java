package com.device.hub.web.Records;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for updating a new device")
public record DeviceUpdateRequest(
    @Schema(description = "Device Name", example = "Computer") String name,
    @Schema(description = "Device Brand", example = "Apple") String brand) {

}
