package org.yamashiro0110.sample.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping(path = "/api/sample/json", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@sampleApiAuthorizer.checkJson(#query, #request)")
    ResponseEntity<?> postJson(@RequestParam(name = "q", required = false) final List<String> query,
                               @RequestBody final SampleRequest request) {
        return ResponseEntity.ok(Map.of(
            "body", request,
            "query", Optional.ofNullable(query).orElse(Collections.emptyList())
        ));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class SampleRequest {
        private String name;
        private String date;
    }

}
