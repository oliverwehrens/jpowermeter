package com.maxheapsize.jpm.reader;

import com.maxheapsize.jpm.SmartMeterReading;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

public interface EhzSmlReader {
    SmartMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException;
}
