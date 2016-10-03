package com.hao.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 上午9:45
 */
@ConfigurationProperties(prefix = "gateway",ignoreUnknownFields = false)
public class GatewayProperties {

    @Getter
    private final Kong kong = new Kong();

    public static class Kong {

        @Getter
        private final Admin admin = new Admin();

        @Getter
        private final Invoke invoke = new Invoke();


        public static class Admin {

            @Getter
            @Setter
            private String host;

            @Getter
            @Setter
            private Integer port;
        }
        public static class Invoke {

            @Getter
            @Setter
            private String timeout;
        }
    }
}
