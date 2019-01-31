package xyz.yazhe.yazheweb.service.domain.blog.DO;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleComment {
    private Integer id;

    private Integer articleId;

    private Integer toCommentId;

    private String content;

    private String createUserId;

    private String updateUserId;

    private Date createTime;

    private Date updateTime;

    private Boolean deleted;
}