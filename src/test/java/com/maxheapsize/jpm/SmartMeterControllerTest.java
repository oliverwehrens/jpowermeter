package com.maxheapsize.jpm;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SmartMeterControllerTest {

    private MockMvc mockMvc;
    private String expectedString;

    @Before
    public void setup() {
        SmartMeterController smartMeterController = new SmartMeterController();
        smartMeterController.readingBuffer = new ReadingBuffer();
        SmartMeterReading smartMeterReading = new SmartMeterReading();
        smartMeterReading.date = new Date();
        expectedString = "{\"date\":" + smartMeterReading.date.getTime() + ",\"meterTotal\":{\"value\":0,\"unit\":\"\"},\"meterOne\":{\"value\":0,\"unit\":\"\"},\"meterTwo\":{\"value\":0,\"unit\":\"\"},\"power\":{\"value\":0,\"unit\":\"\"},\"complete\":false}";
        smartMeterController.readingBuffer.setSmartMeterReading(smartMeterReading);
        this.mockMvc = standaloneSetup(smartMeterController).build();

    }

    @Test
    public void testGet() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedString))
                .andExpect(content().contentType("application/json"));

    }

}
