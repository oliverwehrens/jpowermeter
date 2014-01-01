package com.maxheapsize.jpm;

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import org.openmuc.jsml.structures.*;
import org.openmuc.jsml.tl.SML_SerialReceiver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/*
    Mostly taken from jSML example Code. No questions asks.
 */

@Service
public class EhzSmlReader {

    private static final int LISTENTRY_CONSUMPTION_TOTAL = 3;
    private static final int LISTENTRY_CONSUMPTION_FAREONE = 4;
    private static final int LISTENTRY_CONSUMPTION_FARETWO = 5;
    private static final int LISTENTRY_CONSUMPTION_NOW = 6;

    public PowerMeterReading read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {
        SML_SerialReceiver receiver = new SML_SerialReceiver();
        receiver.setupComPort(device);
        PowerMeterReading powerMeterReading = new PowerMeterReading();

        try {
            for (int j = 0; j < 5; j++) { // no idea why 5 times , this is from the jSML source example
                List<SML_Message> smlMessages = getMessages(receiver);

                for (int i = 0; i < smlMessages.size(); i++) {
                    SML_Message sml_message = smlMessages.get(i);
                    if (isListResponse(sml_message)) {
                        SML_ListEntry[] list = getEntries(sml_message);
                        int listEntryPosition = 0;
                        for (SML_ListEntry entry : list) {
                            listEntryPosition++;
                            int unit = entry.getUnit().getVal();
                            if (unit == SML_Unit.WATT_HOUR || unit == SML_Unit.WATT) {
                                powerMeterReading.date = new Date();
                                Consumption consumption = createConsumption(entry, unit);
                                assignConsumptionToPowerMeter(powerMeterReading, listEntryPosition, consumption);
                            }
                        }
                        return powerMeterReading;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
        } finally {
            receiver.close();
        }
        return powerMeterReading;

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

    private Consumption createConsumption(SML_ListEntry entry, int unit) {
        Consumption consumption = new Consumption();
        consumption.value = getValue(entry.getValue());
        consumption.unit = unit == SML_Unit.WATT ? "W" : "WH";
        return consumption;
    }

    private void assignConsumptionToPowerMeter(PowerMeterReading powerMeterReading, int listEntryPosition, Consumption consumption) {
        switch (listEntryPosition) {
            case LISTENTRY_CONSUMPTION_TOTAL:
                powerMeterReading.consumptionTotal = consumption;
                break;
            case LISTENTRY_CONSUMPTION_FAREONE:
                powerMeterReading.consumptionFareOne = consumption;
                break;
            case LISTENTRY_CONSUMPTION_FARETWO:
                powerMeterReading.consumptionFareTwo = consumption;
                break;
            case LISTENTRY_CONSUMPTION_NOW:
                powerMeterReading.consumptionNow = consumption;
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
