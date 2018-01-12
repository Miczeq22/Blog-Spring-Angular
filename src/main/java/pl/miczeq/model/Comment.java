package pl.miczeq.model;

import java.sql.Date;

public class Comment {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private int likes;
    private Date lastUpdateDate;

    public Comment(Long id, Long userId, String title, String content, int likes, Date lastUpdateDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Comment(Long userId, String title, String content, int likes, Date lastUpdateDate) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.likes = likes;
        this.lastUpdateDate = lastUpdateDate;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getLikes() {
        return likes;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", likes=" + likes +
                ", lastUpdateDate=" + lastUpdateDate +
                '}';
    }
}
