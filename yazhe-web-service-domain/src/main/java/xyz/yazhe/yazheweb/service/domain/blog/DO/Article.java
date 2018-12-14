package xyz.yazhe.yazheweb.service.domain.blog.DO;

import java.util.Date;

/**
 * 文章
 * @author hyz
 */
public class Article {
    private Integer id;

	/**
	 * 创建该文章的用户id
	 */
	private String createdUserId;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 正文
	 */
	private String content;

	/**
	 * 阅读量
	 */
	private Long readingAmount;

	/**
	 * 点赞量
	 */
	private Long praiseClicks;

	/**
	 * 是否删除
	 */
	private Boolean deleted;

	/**
	 * 是否原创
	 */
	private Boolean original;

	/**
	 * 原作者id
	 */
	private String originalAuthorId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId == null ? null : createdUserId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getReadingAmount() {
        return readingAmount;
    }

    public void setReadingAmount(Long readingAmount) {
        this.readingAmount = readingAmount;
    }

    public Long getPraiseClicks() {
        return praiseClicks;
    }

    public void setPraiseClicks(Long praiseClicks) {
        this.praiseClicks = praiseClicks;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getOriginal() {
        return original;
    }

    public void setOriginal(Boolean original) {
        this.original = original;
    }

    public String getOriginalAuthorId() {
        return originalAuthorId;
    }

    public void setOriginalAuthorId(String originalAuthorId) {
        this.originalAuthorId = originalAuthorId == null ? null : originalAuthorId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}