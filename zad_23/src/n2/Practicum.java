package n2;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Practicum {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        double userLatitude = 0;
        double userLongtitude = 0;
        int userTimezone = 0;
        long startOfYear = 0;

        try {
            System.out.println("Введите широту:");
            userLatitude = scanner.nextDouble();

            System.out.println("Введите долготу: ");
            userLongtitude = scanner.nextDouble();

            System.out.println("Введите вашу временную зону: ");
            userTimezone = scanner.nextInt();

            System.out.println("Введите текущий год в формате unix (10 цифр): ");
            startOfYear = scanner.nextLong();

        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: введите корректное числовое значение.");
            scanner.close();
            return;
        }

        Instant startOfYearMoment = Instant.ofEpochSecond(startOfYear);

        Instant thisMoment = startOfYearMoment;

        Instant lastMoment = startOfYearMoment.plus(7, ChronoUnit.DAYS);

        System.out.println("Рассвет - Закат, график на неделю:");
        do {
            int day = dayOfYearFromInstant(startOfYearMoment, thisMoment);

            int noonMinutes = localNoonMinutes(day, userTimezone, userLongtitude);
            double hourDelta = sunsetTimeHours(day, userLatitude);
            double noonHour = 12 + (noonMinutes / 60.0);
            double sunriseHour = noonHour - hourDelta;
            double sunsetHour = noonHour + hourDelta;

            System.out.printf("%s - %s\n", hhmmFromDouble(sunriseHour), hhmmFromDouble(sunsetHour));

            thisMoment = thisMoment.plus(1, ChronoUnit.DAYS);
        } while (thisMoment.isBefore(lastMoment));

        scanner.close();
    }

    private static int dayOfYearFromInstant(Instant startOfYear, Instant time) {
        long fromStartOfYear = startOfYear.until(time, ChronoUnit.DAYS);
        return (int) fromStartOfYear + 1;
    }

    static double sunsetTimeHours(int dayOfYear, double latitude){
        double rad = (Math.PI / 180);
        double factor = -1 * Math.tan(rad * latitude) * Math.tan(23.44 * rad * Math.sin(rad * (360 / 365.0) * (dayOfYear + 284.0)));
        if(factor <= -1 || factor >= 1){
            return 0.0;
        }
        return Math.abs(Math.acos(factor)) / (rad * 15);
    }

    static int equationOfTimeMinutes(int dayOfYear){
        return (int) Math.round(-7.655 * Math.sin(2 * Math.PI * dayOfYear / 365) + 9.873 * Math.sin(4 * Math.PI * dayOfYear / 365 + 3.588));
    }

    static int localNoonMinutes(int dayOfYear, int timeZone, double longtitude){
        return (int) Math.round(4.0 * (longtitude - (15 * (timeZone - 1)))) - equationOfTimeMinutes(dayOfYear);
    }

    static String hhmmFromDouble(double hour){
        return String.format("%d:%02d", (int) Math.floor(hour), (int) Math.round(60 * (hour - Math.floor(hour))));
    }

}