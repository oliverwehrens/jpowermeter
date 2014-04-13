package com.maxheapsize.jpm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface PowerMeterReadingRepository extends CrudRepository<PowerMeterReading, Long> {

    @Query("SELECT r from PowerMeterReading r WHERE r.date >= :startDate and r.date <= :endDate ORDER BY r.date ASC")
    List<PowerMeterReading> findX(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
