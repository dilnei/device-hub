package com.device.hub.web.Records;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request object for creating a new device")
public record DeviceCreateRequest(
    @Schema(description = "Device Name", example = "Computer")
    @NotBlank
    @NotNull
    @NotEmpty
    String name,

    @Schema(description = "Device Brand", example = "Apple")
    @NotBlank
    @NotNull
    @NotEmpty
    String brand) {

}
