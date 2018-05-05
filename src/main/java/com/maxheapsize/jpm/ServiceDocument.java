package com.maxheapsize.jpm;

import java.util.HashSet;
import java.util.Set;

public class ServiceDocument {

    Set devices = new HashSet();

    public ServiceDocument(Set devices) {
        this.devices = devices;
    }

    public Set getDevices() {
        return devices;
    }


}
