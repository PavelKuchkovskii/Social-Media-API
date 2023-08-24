package org.kucher.userservice.config.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Converter class to convert a string representation of a timestamp to a {@link LocalDateTime} object.
 */
@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    /**
     * Converts a string representation of a timestamp to a {@link LocalDateTime} object.
     *
     * @param source The string representation of the timestamp.
     * @return A {@link LocalDateTime} object representing the converted timestamp.
     */
    @Override
    public LocalDateTime convert(String source) {
        long timestamp = Long.parseLong(source);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"));
    }
}