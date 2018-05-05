package com.maxheapsize.jpm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

@Service
public class DeviceNameSplitter {

    private final static Logger log = LoggerFactory.getLogger(DeviceNameSplitter.class);

    List<String> split(String devices) {
        String splittedDevices[] = devices.split(",");
        List<String> deviceList = new ArrayList<>();
        for (String device : splittedDevices) {
            deviceList.add(device.trim());
            log.info("Added device {} to the device list.", device.trim());
        }
        if (deviceList.size() < 1) {
            log.error("No devices configured.");
            exit(1);
        }
        return deviceList;
    }

}
