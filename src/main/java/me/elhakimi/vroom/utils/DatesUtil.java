package me.elhakimi.vroom.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DatesUtil {

    public static long getDifferenceInDays(LocalDateTime startDate, LocalDateTime endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

}
