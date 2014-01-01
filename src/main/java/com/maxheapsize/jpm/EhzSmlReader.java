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

    private static final int COUNTER_CONSUMPTION_TOTAL = 3;
    private static final int COUNTER_CONSUMPTION_FAREONE = 4;
    private static final int COUNTER_CONSUMPTION_FARETWO = 5;
    private static final int COUNTER_CONSUMPTION_NOW = 6;

    public PowerMeter read(String device) throws PortInUseException, IOException, UnsupportedCommOperationException {
        SML_SerialReceiver receiver = new SML_SerialReceiver();
        receiver.setupComPort(device);
        PowerMeter powerMeter = new PowerMeter();

        try {

            for (int j = 0; j < 5; j++) {
                SML_File smlFile = receiver.getSMLFile();

                List<SML_Message> smlMessages = smlFile.getMessages();

                for (int i = 0; i < smlMessages.size(); i++) {

                    SML_Message sml_message = smlMessages.get(i);

                    int tag = sml_message.getMessageBody().getTag().getVal();
                    switch (tag) {
                        case SML_MessageBody.GetListResponse:
                            SML_GetListRes resp = (SML_GetListRes) sml_message.getMessageBody().getChoice();
                            SML_List smlList = resp.getValList();
                            SML_ListEntry[] list = smlList.getValListEntry();
                            int counter = 0;
                            for (SML_ListEntry entry : list) {
                                counter++;
                                int unit = entry.getUnit().getVal();
                                if (unit == SML_Unit.WATT_HOUR || unit == SML_Unit.WATT) {
                                    powerMeter.date = new Date();
                                    Power power = new Power();
                                    power.value = getValue(entry.getValue());
                                    power.unit = unit == SML_Unit.WATT ? "W" : "WH";

                                    switch (counter) {
                                        case COUNTER_CONSUMPTION_TOTAL:
                                            powerMeter.consumptionTotal = power;
                                            break;
                                        case COUNTER_CONSUMPTION_FAREONE:
                                            powerMeter.consumptionFareOne = power;
                                            break;
                                        case COUNTER_CONSUMPTION_FARETWO:
                                            powerMeter.consumptionFareTwo = power;
                                            break;
                                        case COUNTER_CONSUMPTION_NOW:
                                            powerMeter.consumptionNow = power;
                                            break;
                                        default:
                                            break;
                                    }
                                }

                            }
                            return powerMeter;
                        default:
                            ;

                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception " + e);
        } finally {
            receiver.close();
        }
        return powerMeter;

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
