package cn.ridup.base.mix.push.service;

import lombok.Data;

/**
 * 文章
 *
 * @author ridup
 * @version 0.1.0
 * @since 2022/11/2 17:39
 */
@Data
public class ArticleDto {
    private String title;
    private String thumb_media_id;
    private String author;
    private String content_source_url;
    private String content;
    private String digest;
}
