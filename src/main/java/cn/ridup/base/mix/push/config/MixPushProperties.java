package cn.ridup.base.mix.push.config;

import java.io.File;
import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import lombok.Data;

/**
 * yuque configuration properties.
 *
 * @author ridup
 * @version 0.1.0
 * @since 2022/3/29 20:41
 */
@Data
@ConfigurationProperties(prefix = MixPushProperties.PREFIX)
public class MixPushProperties {

    public final static String PREFIX = "mix-push";

    /**
     * 企业ID
     */
    private String companyId = "";

    /**
     * 企业应用ID
     */
    private String agentId = "";

    /**
     * 企业应用密钥
     */
    private String secret = "";

    /**
     * 通知方式
     */
    private String notify = "@all";

    /**
     * 图片ID
     */
    private String mediaId = "";

    /**
     * Download Timeout.
     */
    private Duration downloadTimeout = Duration.ofSeconds(30);

    /**
     * Ensures the string contain suffix.
     *
     * @param string string must not be blank
     * @param suffix suffix must not be blank
     * @return string contain suffix specified
     */
    @NonNull
    public static String ensureSuffix(@NonNull String string, @NonNull String suffix) {
        Assert.hasText(string, "String must not be blank");
        Assert.hasText(suffix, "Suffix must not be blank");

        return StringUtils.removeEnd(string, suffix) + suffix;
    }

}
