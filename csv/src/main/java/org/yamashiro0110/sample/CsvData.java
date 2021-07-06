package org.yamashiro0110.sample;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonIgnore
    private String ignoreField = "ignore";
    @JsonProperty("alias")
    private String aliasField = "alias";

    public CsvData(final int number) {
        this.number = number;
    }
}
