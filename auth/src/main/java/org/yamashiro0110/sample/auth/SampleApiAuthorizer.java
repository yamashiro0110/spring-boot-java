package org.yamashiro0110.sample.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("sampleApiAuthorizer")
@Slf4j
public class SampleApiAuthorizer {

    public boolean checkJson(final List<String> query, final SampleApi.SampleRequest request) {
        log.info("クエリパラメータを確認 {}", query);
        log.info("リクエストボディを確認 {}", request);
        return true;
    }

}
