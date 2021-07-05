package org.yamashiro0110.sample;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@NoArgsConstructor
@Data
public class CsvData {
    private Integer number;
    private String uuid = UUID.randomUUID().toString();
    private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    public CsvData(final int number) {
        this.number = number;
    }
}
