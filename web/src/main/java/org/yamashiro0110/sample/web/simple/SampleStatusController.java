package org.yamashiro0110.sample.web.simple;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/status")
public class SampleStatusController {

    @GetMapping("200")
    ResponseEntity<?> ok() {
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

    @GetMapping("302")
    ResponseEntity<?> redirect() {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
    }

    @GetMapping("400")
    ResponseEntity<?> error400() {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of("result", "400"));
    }

    @GetMapping("401")
    ResponseEntity<?> error401() {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("result", "401"));
    }

    @GetMapping("403")
    ResponseEntity<?> error403() {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(Map.of("result", "403"));
    }

    @GetMapping("404")
    ResponseEntity<?> error404() {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Map.of("result", "404"));
    }

    @GetMapping("500")
    ResponseEntity<?> error500() {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("result", "500"));
    }

    @GetMapping("502")
    ResponseEntity<?> error502() {
        return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(Map.of("result", "502"));
    }

    @GetMapping("503")
    ResponseEntity<?> error503() {
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of("result", "503"));
    }

}
