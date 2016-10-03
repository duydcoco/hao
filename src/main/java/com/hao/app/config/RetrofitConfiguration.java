package com.hao.app.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Inject;

/**
 * @author dongh38@ziroom
 * @Date 16/10/3
 * @Time 上午9:55
 */
@Configuration
@Slf4j
public class RetrofitConfiguration {

    @Inject
    private Jackson2ObjectMapperBuilder builder;

    private String getKongAdminAddress(GatewayProperties.Kong.Admin admin) {
        String host = admin.getHost();
        if (StringUtils.isNotBlank(host)) {
            return host.startsWith("http") ? host : "http://" + host;
        }
        log.error("kong管理地址未配置,采用缺省地址");
        return "http://127.0.0.1";
    }

    private Integer getKongAdminPort(GatewayProperties.Kong.Admin admin) {
        Integer port = admin.getPort();
        if (port == null) {
            log.error("kong的管理端口未配置,使用缺省端口[8001]");
            return 8001;
        }
        return port;
    }

    private String getKongAdminUrl(GatewayProperties.Kong kong) {
        return getKongAdminAddress(kong.getAdmin()) + ":" + getKongAdminPort(kong.getAdmin());
    }

    @Bean
    public Retrofit retrofit(GatewayProperties gatewayProperties) {
        return new Retrofit.Builder()
                .baseUrl(getKongAdminUrl(gatewayProperties.getKong()))
                .addConverterFactory(JacksonConverterFactory.create(builder.build()))
                .build();
    }

}
