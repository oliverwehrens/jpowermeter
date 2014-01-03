package com.maxheapsize.jpm;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MockEhzSmlReader implements EhzSmlReader {

    public PowerMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {
        //TODO
        throw new UnsupportedOperationException();
    }
}
