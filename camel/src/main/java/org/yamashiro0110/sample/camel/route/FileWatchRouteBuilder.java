package org.yamashiro0110.sample.camel.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @see <a href="https://camel.apache.org/components/latest/file-watch-component.html">file-watch-component</a>
 */
@Component
@Slf4j
public class FileWatchRouteBuilder extends EndpointRouteBuilder {
    private final String directory = "tmp/";

    @Override
    public void configure() throws Exception {
        this.from(this.fileWatch(this.directory).recursive(true).autoCreate(true))
            .routeId("fileWatch-tmpDir")
            .log("File event: ${header.CamelFileEventType} occurred on file ${header.CamelFileName} at ${header.CamelFileLastModified}")
            .process(exchange -> log.info("exchange {}", exchange.getIn().getBody()));

        this.from(this.timer("create_file").period(60000).delay(3000))
            .routeId("createFile-tmp")
            .process(exchange -> this.createFile())
            .log("File create");
    }

    void createFile() throws IOException {
        final String fileName = String.format("%s.%s", System.currentTimeMillis(), "txt");
        final Path file = Paths.get(this.directory, fileName);
        Files.createFile(file);
        Files.writeString(file, fileName);
    }

    @Bean
    CommandLineRunner mkFileCmdRunner() {
        return args -> this.createFile();
    }

}
