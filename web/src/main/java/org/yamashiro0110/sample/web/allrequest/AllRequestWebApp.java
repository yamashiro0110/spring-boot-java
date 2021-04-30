package org.yamashiro0110.sample.web.allrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SpringBootApplication
@RestController
public class AllRequestWebApp {

    public static void main(final String[] args) {
        SpringApplication.run(AllRequestWebApp.class, args);
    }

    /**
     * 全てのリクエストを受信する
     *
     * @param request
     * @return
     */
    @RequestMapping(path = {"**", "/**"})
    ResponseEntity<?> endpoint(final HttpServletRequest request) {
        final Map<String, ?> data = Map.of(
            "path", request.getRequestURI(),
            "param", request.getParameterMap()
        );

        return ResponseEntity.ok(data);
    }

}
