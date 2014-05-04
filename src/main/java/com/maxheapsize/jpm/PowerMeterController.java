package com.maxheapsize.jpm;

import com.maxheapsize.jpm.panicstatusboard.DataPoint;
import com.maxheapsize.jpm.panicstatusboard.DataSequence;
import com.maxheapsize.jpm.panicstatusboard.Graph;
import com.maxheapsize.jpm.panicstatusboard.GraphO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class PowerMeterController {

    @Autowired
    private PowerMeterValueService powerMeterValueService;

    @Autowired
    private PowerMeterReadingRepository powerMeterReadingRepository;

    private BigDecimal pricekwh = new BigDecimal(0.2642);

    @RequestMapping(value = "/today", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    BigDecimal measureToday() {
        LocalDate now = LocalDate.now();
        List<PowerMeterReading> readings = getByDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        if (readings.isEmpty()) {
            return new BigDecimal(0);
        }
        BigDecimal value = readings.get(readings.size() - 1).consumptionTotal.value.subtract(readings.get(0).consumptionTotal.value);
        return (value.divide(new BigDecimal(1000)).multiply(pricekwh)).setScale(2, RoundingMode.HALF_UP);
    }

    @RequestMapping(value = "/all", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PowerMeterReading> all() {
        Iterable<PowerMeterReading> all = powerMeterReadingRepository.findAll();
        List<PowerMeterReading> result = new ArrayList();
        for (PowerMeterReading powerMeterReading : all) {
            result.add(powerMeterReading);
        }
        return result;

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public
    @ResponseBody
    ModelAndView web() {
        ModelAndView model = new ModelAndView();
        model.getModelMap().addAttribute("reading", powerMeterValueService.getPowerMeterReading());
        model.setViewName("home");
        return model;
    }

    private List<PowerMeterReading> getByDate(int year, int month, int day) {
        LocalDateTime start = LocalDateTime.of(year, month, day, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, month, day, 23, 59, 59);

        Date startDate = Date.from(Instant.from(start.atZone(ZoneId.systemDefault())));
        Date endDate = Date.from(Instant.from(end.atZone(ZoneId.systemDefault())));

        List<PowerMeterReading> readings = powerMeterReadingRepository.findDateInRange(startDate, endDate);
        return readings;
    }

    private List<PowerMeterReading> getReadingsForLast24h() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twentyFourHoursAgo = now.minusHours(24);
        Date startDate = Date.from(Instant.from(now.atZone(ZoneId.systemDefault())));
        Date endDate = Date.from(Instant.from(twentyFourHoursAgo.atZone(ZoneId.systemDefault())));
        return powerMeterReadingRepository.findDateInRange(startDate, endDate);
    }

    @RequestMapping(value = "/consumption/now", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    Consumption getConsumptionNowJson() {
        return powerMeterValueService.getPowerMeterReading().consumptionNow;
    }

    @RequestMapping(value = "/consumption/now/price", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    Consumption getConsumptionNowJsonPrice() {
       return powerMeterValueService.getPowerMeterReading().consumptionNow;
    }

    @RequestMapping(value = "/consumption/now/text", produces = "text/text", method = RequestMethod.GET)
    public
    @ResponseBody
    String getConsumptionNowText() {
        return powerMeterValueService.getPowerMeterReading().consumptionNow.toString();
    }

    @RequestMapping(value = "/counter/now", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    Consumption getCounterNowJson() {
        return powerMeterValueService.getPowerMeterReading().consumptionTotal;
    }

    @RequestMapping(value = "/last24h/panicstatusboard", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    GraphO measureLast24h() {
//        List<PowerMeterReading> readings = getReadingsForLast24h();
        LocalDate now = LocalDate.now();
        List<PowerMeterReading> readings = getByDate(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        GraphO go = createPanicGraph(readings);
        return go;
    }

    private GraphO createPanicGraph(List<PowerMeterReading> readings) {
        LocalDateTime afterThisNewDataPointNeededTime = LocalDateTime.now().minusDays(2);
        GraphO go = new GraphO();
        Graph graph = new Graph();
        go.graph = graph;
        graph.title = "Verbrauch 24h";
        graph.type = "line";
        DataSequence dataSequence = new DataSequence();
        dataSequence.title = "WH";
        graph.datasequences.add(dataSequence);

        if (!readings.isEmpty()) {
            BigDecimal lastConsumption = readings.get(0).consumptionTotal.value;

            for (PowerMeterReading reading : readings) {
                Instant instant = Instant.ofEpochMilli(reading.date.getTime());
                LocalDateTime measureTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                if (measureTime.isAfter(afterThisNewDataPointNeededTime)) {
                    afterThisNewDataPointNeededTime = measureTime.plusMinutes(5);
                    DataPoint point = new DataPoint();
                    point.title = measureTime.getHour() + ":" + measureTime.getMinute();
                    point.value = reading.consumptionTotal.value.subtract(lastConsumption);
                    lastConsumption = reading.consumptionTotal.value;
                    dataSequence.datapoints.add(point);
                }
            }
        }
        return go;
    }


}
