package com.maxheapsize.jpm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends RuntimeException {
    String device;

    public DeviceNotFoundException(String message, String device) {
        super(message);
        this.device = device;
    }
}
