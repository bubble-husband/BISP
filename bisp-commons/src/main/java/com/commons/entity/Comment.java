package com.commons.entity;

public class Comment {
    private Integer commentId;

    private String commentContent;

    private Integer articleId;

    private Integer userId;

    private Integer supCommentId;

    private Byte authorRepty;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSupCommentId() {
        return supCommentId;
    }

    public void setSupCommentId(Integer supCommentId) {
        this.supCommentId = supCommentId;
    }

    public Byte getAuthorRepty() {
        return authorRepty;
    }

    public void setAuthorRepty(Byte authorRepty) {
        this.authorRepty = authorRepty;
    }
}