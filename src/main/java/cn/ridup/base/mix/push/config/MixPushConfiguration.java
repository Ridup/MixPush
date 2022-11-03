package cn.ridup.base.mix.push.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import cn.ridup.base.mix.push.service.Pusher;
import lombok.extern.slf4j.Slf4j;

/**
 * Yuque configuration.
 *
 * @author ridup
 * @version 0.1.0
 * @since 2022/3/29 20:41
 */
@Slf4j
@EnableAsync
@EnableCaching
@EnableScheduling
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MixPushProperties.class)
public class MixPushConfiguration {

    private final MixPushProperties mixPushProperties;

    public MixPushConfiguration(MixPushProperties mixPushProperties) {
        this.mixPushProperties = mixPushProperties;
    }

    @Bean(initMethod = "init")
    public Pusher pusher() throws Exception {
        Pusher pusher = Pusher.builder()
            .companyId(mixPushProperties.getCompanyId())
            .agentId(mixPushProperties.getAgentId())
            .secret(mixPushProperties.getSecret())
            .notify(mixPushProperties.getNotify())
            .mediaId(mixPushProperties.getMediaId())
            .build();
        log.info("[init pusher , MixPushProperties ={}]", mixPushProperties);
        return pusher;
    }

}
