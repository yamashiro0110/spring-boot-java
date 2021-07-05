package org.yamashiro0110.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = {"largeData"})
@Accessors(fluent = true)
public class CacheData implements Serializable {
    private String id = UUID.randomUUID().toString();
    private LocalDateTime created = LocalDateTime.now();
    private String largeData = StringUtils.repeat("data", 1024 * 1024 * 5);

    public static String cacheId(final long number) {
        return String.format("%s:%s", CacheData.class.getName(), number);
    }

}
