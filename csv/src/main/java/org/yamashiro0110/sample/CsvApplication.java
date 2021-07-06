package org.yamashiro0110.sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
public class CsvApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CsvApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            final CsvMapper csvMapper = new CsvMapper();

            final CsvSchema csvSchema = csvMapper.schemaFor(CsvData.class)
                .withHeader()
                .withQuoteChar('"')
                .withColumnSeparator(',');

            IntStream.rangeClosed(0, 3)
                .mapToObj(CsvData::new)
                .forEach(data -> {
                    try {
                        final String csv = csvMapper.writer(csvSchema).writeValueAsString(data);
                        System.out.println(csv);
                    } catch (final JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
        };
    }

}
