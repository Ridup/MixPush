package cn.ridup.base.mix.push.service;

import java.io.Serializable;

import lombok.Data;

/**
 * TODO
 *
 * @author ridup
 * @version 0.1.0
 * @since 2022/11/2 17:14
 */
@Data
public class QueryAccessTokenRequest implements Serializable {
    private static final long serialVersionUID = 6529503278952441226L;

    private String corpid;

    private String corpsecret;
}
