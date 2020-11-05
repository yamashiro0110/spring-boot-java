package org.yamashiro0110.sample.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestController
public class SampleLogController {
    final Logger logger = LoggerFactory.getLogger(SampleLogController.class);
    final Logger requestLog = LoggerFactory.getLogger("requestLog");
    final ObjectMapper objectMapper = new ObjectMapper();

    @ModelAttribute
    void log(final WebRequest request) throws JsonProcessingException {
        this.requestLog.info(this.objectMapper.writeValueAsString(request.getParameterMap()));
    }

    @GetMapping("/sample")
    ResponseEntity<?> log() {
        this.logger.info("sampleが実行されました");
        return ResponseEntity.ok(Map.of("result", "ok"));
    }

}
