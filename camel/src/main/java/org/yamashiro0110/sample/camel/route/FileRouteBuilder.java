package org.yamashiro0110.sample.camel.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRouteBuilder extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {
        this.from(this.file("tmp/case-1/in").recursive(true).delete(true))
            .routeId("file-1")
            .log("Poll File: ${header.CamelFileName}")
            .to(this.file("tmp/case-1/out"))
            .log("Move File: ${header.CamelFileName}")
            .end();

        this.from(this.file("tmp/case-2/in").recursive(true).delete(true).delay(60000).repeatCount(0))
            .routeId("file-2")
            .log("Poll File: ${header.CamelFileName}")
            .process(exchange -> {
                throw new IllegalStateException("エラーを発生させる");
            })
            .to(this.file("tmp/case-2/out"))
            .log("Move File: ${header.CamelFileName}")
            .end();

        this.from(this.file("tmp/case-3/in").recursive(true).preMove(".wip").delete(true))
            .routeId("file-3")
            .log("Poll File: ${header.CamelFileName}")
            .to(this.file("tmp/case-3/out"))
            .log("Move File: ${header.CamelFileName}")
            .end();
    }
}
