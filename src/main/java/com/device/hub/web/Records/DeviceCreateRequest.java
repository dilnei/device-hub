package com.device.hub.web.Records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DeviceCreateRequest(
    @NotBlank
    @NotNull
    @NotEmpty
    String name,

    @NotBlank
    @NotNull
    @NotEmpty
    String brand) {

}
