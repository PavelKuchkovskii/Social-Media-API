package org.kucher.socialservice.utill.Time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Utility class for handling time-related operations.
 */
public class TimeUtil {

    /**
     * Returns the current date and time in the UTC time zone with millisecond precision.
     *
     * @return The current date and time with millisecond precision in UTC.
     */
    public static LocalDateTime now() {
        // Get the current time in milliseconds and convert to LocalDateTime
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()),
                ZoneOffset.UTC
        );
    }
}
