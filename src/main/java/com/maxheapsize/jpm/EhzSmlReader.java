package com.maxheapsize.jpm;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

public interface EhzSmlReader {
    PowerMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException;
}
