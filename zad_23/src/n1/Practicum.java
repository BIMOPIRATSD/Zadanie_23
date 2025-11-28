package n1;

import java.time.Instant;

public class Practicum {
    public static void main(String[] args) {
        long millis = 9_000_000_000L;

        System.out.println(Instant.ofEpochMilli(millis));
        System.out.println(Instant.ofEpochMilli(-millis));
    }
}
