package com.maxheapsize.jpm.reader;

import com.maxheapsize.jpm.Meter;
import com.maxheapsize.jpm.SmartMeterReading;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.openmuc.jsml.structures.*;
import org.openmuc.jsml.tl.SML_SerialReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*
    Mostly taken from jSML example Code. No questions asks.
 */

@Service
public class DeviceEhzSmlReader implements EhzSmlReader {

    private static final int LISTENTRY_CONSUMPTION_TOTAL = 3;
    private static final int LISTENTRY_CONSUMPTION_FAREONE = 4;
    private static final int LISTENTRY_CONSUMPTION_FARETWO = 5;
    private static final int LISTENTRY_CONSUMPTION_NOW = 6;
    private final static Logger log = LoggerFactory.getLogger(DeviceEhzSmlReader.class);
    private final int TRIES_TO_GET_THE_START_SEQUENCE_IN_DATA_FROM_DEVICE = 1;

    public SmartMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {
        SML_SerialReceiver receiver = new SML_SerialReceiver();
        receiver.setupComPort(device);
        SmartMeterReading smartMeterReading = new SmartMeterReading();

        try {
            for (int j = 0; j < TRIES_TO_GET_THE_START_SEQUENCE_IN_DATA_FROM_DEVICE; j++) {
                List<SML_Message> smlMessages = getMessages(receiver);

                for (SML_Message sml_message : smlMessages) {
                    if (isListResponse(sml_message)) {
                        SML_ListEntry[] list = getEntries(sml_message);
                        int listEntryPosition = 0;
                        for (SML_ListEntry entry : list) {
                            listEntryPosition++;
                            int unit = entry.getUnit().getVal();
                            Integer8 scaler = entry.getScaler();
                            if (unit == SML_Unit.WATT_HOUR || unit == SML_Unit.WATT) {
                                smartMeterReading.date = new Date();
                                Meter meter = extractMeter(entry, unit, scaler);
                                assignConsumptionToMatchingPowerMeterField(smartMeterReading, listEntryPosition, meter);
                            }
                        }
                    }
                }
                log.debug(smartMeterReading.toString());
            }
        } catch (Exception e) {
            log.error("Exception {}", e.getMessage());
            smartMeterReading.complete = false;
        } finally {
            receiver.close();
        }
        return smartMeterReading;
    }

    private boolean isListResponse(SML_Message sml_message) {
        return sml_message.getMessageBody().getTag().getVal() == SML_MessageBody.GetListResponse;
    }

    private List<SML_Message> getMessages(SML_SerialReceiver receiver) throws IOException {
        SML_File smlFile = receiver.getSMLFile();
        return smlFile.getMessages();
    }

    private SML_ListEntry[] getEntries(SML_Message sml_message) {
        SML_GetListRes resp = (SML_GetListRes) sml_message.getMessageBody().getChoice();
        SML_List smlList = resp.getValList();
        return smlList.getValListEntry();
    }

    private Meter extractMeter(SML_ListEntry entry, int unit, Integer8 scaler) {
        Meter meter = new Meter();
        double pow = Math.pow(10, scaler.getVal());
        meter.value = BigDecimal.valueOf((getValue(entry.getValue()) / (1 / pow)));
        meter.unit = unit == SML_Unit.WATT ? "W" : "WH";
        return meter;
    }

    private void assignConsumptionToMatchingPowerMeterField(SmartMeterReading smartMeterReading, int listEntryPosition, Meter meter) {
        switch (listEntryPosition) {
            case LISTENTRY_CONSUMPTION_TOTAL:
                smartMeterReading.meterTotal = meter;
                break;
            case LISTENTRY_CONSUMPTION_FAREONE:
                smartMeterReading.meterOne = meter;
                break;
            case LISTENTRY_CONSUMPTION_FARETWO:
                smartMeterReading.meterTwo = meter;
                break;
            case LISTENTRY_CONSUMPTION_NOW:
                smartMeterReading.power = meter;
                break;
            default:
                break;
        }
    }

    private Long getValue(SML_Value value) {
        Long result = (long) 0;
        ASNObject obj = value.getChoice();
        if (obj.getClass().equals(Integer32.class)) {
            Integer32 val = (Integer32) obj;
            result = (long) val.getVal();
        } else if (obj.getClass().equals(Integer64.class)) {
            Integer64 val = (Integer64) obj;
            result = val.getVal();
        }
        return result;
    }
}
