package com.icrn.substitute.scheduler.substitute_scheduler.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class DateWrapper {
    private LocalDate startDay;
    private LocalTime startTime, endTime;

    public static DateWrapper dateWrapperNow(){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateWrapper dateWrapper = new DateWrapper();
        dateWrapper.startDay = localDateTime.toLocalDate();
        dateWrapper.startTime = localDateTime.toLocalTime();
        dateWrapper.endTime = localDateTime.toLocalTime();
        return dateWrapper;
    }

    public DateWrapper(LocalDate startDay, LocalTime startTime, LocalTime endTime) {
        this.startDay = startDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
