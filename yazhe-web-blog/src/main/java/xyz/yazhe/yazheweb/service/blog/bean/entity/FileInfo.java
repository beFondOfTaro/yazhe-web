package xyz.yazhe.yazheweb.service.blog.bean.entity;

import lombok.Data;

import java.util.Date;

@Data
public class FileInfo {
    private Integer id;

    private String name;

    private String url;

    private Integer type;

    private Long size;

    private String createUserId;

    private String updateUserId;

    private Date createTime;

    private Date updateTime;
}