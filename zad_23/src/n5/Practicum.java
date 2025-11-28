package n5;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class Practicum {
    public static void main(String[] args) {
        LocalDate secretDate = LocalDate.of(2020, 1, 10);
        LocalTime secretTime = LocalTime.of(12, 30);

        int result = decode(secretDate, secretTime);
        System.out.println(result);
    }

    private static int decode(LocalDate secretDate, LocalTime secretTime) {

        LocalDateTime newTime = LocalDateTime.of(secretDate, secretTime);

        LocalDateTime secretMoment = newTime.minusMonths(2).minusDays(25).minusMinutes(100);

        return secretMoment.getDayOfYear() * secretMoment.getHour();
    }
}
