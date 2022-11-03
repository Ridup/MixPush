package cn.ridup.base.mix.push.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 发送对象
 *
 * @author ridup
 * @version 0.1.0
 * @since 2022/11/2 17:14
 */
@Data
public class SendRequest implements Serializable {
    private static final long serialVersionUID = 6529503278952441226L;

    private String touser;
    private String agentid;
    private String safe = "0";
    private String msgtype = "mpnews";
    private Map<String, List<ArticleDto>> mpnews;
}
