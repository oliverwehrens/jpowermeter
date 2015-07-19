package com.maxheapsize.jpm.reader;

import com.maxheapsize.jpm.SmartMeterReadout;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

public interface EhzSmlReader {
    SmartMeterReadout read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException;
}
