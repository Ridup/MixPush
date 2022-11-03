package cn.ridup.base.mix.push.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import cn.ridup.base.mix.push.config.MixPushProperties;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * 发送器
 *
 * @author ridup
 * @version 0.1.0
 * @since 2022/11/2 16:40
 */
@Builder
@Slf4j
public class Pusher {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private MixPushProperties mixPushProperties;

    private final String URL;

    /**
     * 企业ID
     */
    private String companyId;

    /**
     * 企业应用ID
     */
    private String agentId;

    /**
     * 企业应用密钥
     */
    private String secret;

    /**
     * 通知方式
     */
    private String notify;

    /**
     * 图片ID
     */
    private String mediaId;

    private String host;

    private String accessTokenFetchUrl;

    private String sendFetchUrl;

    private void init() {
        this.host = "https://qyapi.weixin.qq.com/cgi-bin";
        this.accessTokenFetchUrl = host + "/gettoken";
        this.sendFetchUrl = host + "/message/send?access_token=";
        System.out.println("init" + companyId);
    }

    private <E, T> T fetchForEntityWithHeader(String url, HttpMethod method, E request,
        ParameterizedTypeReference<T> type) {
        HttpEntity<E> httpEntity = new HttpEntity<>(request, getDefaultHttpHeader(true));
        ResponseEntity<T> haloCommonDtoResponseEntity = restTemplate.exchange(url, method, httpEntity, type);
        // if (null != haloCommonDtoResponseEntity.getBody() && haloCommonDtoResponseEntity.getBody()
        //     .getStatus()
        //     .equals(org.springframework.http.HttpStatus.UNAUTHORIZED.value())) {
        //     login(yuqueProperties.getHalo()
        //         .getUsername(), yuqueProperties.getHalo()
        //         .getPassword());
        // }
        return haloCommonDtoResponseEntity.getBody();
    }

    /**
     * 获取默认的请求头
     *
     * @param tokenCheck 是否需要检查token
     * @return HttpHeaders 请求头
     */
    private HttpHeaders getDefaultHttpHeader(boolean tokenCheck) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

    /**
     * 获取Token
     *
     * @return
     */
    public String getAccessToken() {
        QueryAccessTokenRequest request = new QueryAccessTokenRequest();
        request.setCorpid(companyId);
        request.setCorpsecret(secret);
        log.info("fetch access token by companyId={} secret={}", companyId, secret);
        Map<String, String> map = fetchForEntityWithHeader(accessTokenFetchUrl, HttpMethod.POST, request,
            new ParameterizedTypeReference<Map>() {
            });
        log.info("access_token={}", map.get("access_token"));
        return map.get("access_token");
    }

    /**
     * 发送消息
     *
     * @param articleDto
     * @return
     */
    public boolean send(ArticleDto articleDto) {
        SendRequest request = new SendRequest();
        request.setTouser(mixPushProperties.getNotify());
        request.setAgentid(mixPushProperties.getAgentId());
        request.setSafe("0");
        request.setMsgtype("mpnews");
        HashMap<String, List<ArticleDto>> stringListHashMap = new HashMap<>();
        if (StringUtils.isBlank(articleDto.getThumb_media_id())) {
            articleDto.setThumb_media_id(mediaId);
        }
        stringListHashMap.put("articles", List.of(articleDto));
        request.setMpnews(stringListHashMap);

        Map map = fetchForEntityWithHeader(sendFetchUrl + getAccessToken(), HttpMethod.POST, request,
            new ParameterizedTypeReference<Map>() {
            });
        if (map.get("errcode") != "0") {
            log.error("企业微信应用消息发送通知消息失败！！\n原因：{}", map.get("errmsg"));
            return false;
        }
        log.info("企业微信应用消息发送通知消息成功🎉");
        return true;
    }

}
