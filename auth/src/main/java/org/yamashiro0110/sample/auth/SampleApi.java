package org.yamashiro0110.sample.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SampleApi {

    @GetMapping("/")
    ResponseEntity<?> index() {
        return ResponseEntity.ok(Map.of("resource", "index"));
    }

    @GetMapping("/api/sample")
    ResponseEntity<?> get(@RequestParam(name = "p", defaultValue = "none") final String param) {
        return ResponseEntity.ok(Map.of("resource", "sample", "param", param));
    }

    @PostMapping("/api/sample")
    ResponseEntity<?> post(@RequestBody final String data) {
        return ResponseEntity.ok(Map.of("resource", "sample", "data", data));
    }

}
