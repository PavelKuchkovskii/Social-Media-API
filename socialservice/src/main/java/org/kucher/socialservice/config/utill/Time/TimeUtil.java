package org.kucher.socialservice.config.utill.Time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtil {

    //Return LocalDateTime only with Milli
    public static LocalDateTime now() {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()), ZoneOffset.UTC);
    }
}
