package pl.miczeq.model;

import java.sql.Date;
import java.util.List;

public class Article {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private int likes;
    private Date lastUpdateDate;
    private String imgUrl;
    private List<Category> categories;

    public Article(Long id, String title, String content, Long userId, int likes, Date lastUpdateDate, String imgUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.likes = likes;
        this.lastUpdateDate = lastUpdateDate;
        this.imgUrl = imgUrl;
        this.categories = categories;
    }

    public Article(String title, String content, Long userId, int likes, Date lastUpdateDate, String imgUrl) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.likes = likes;
        this.lastUpdateDate = lastUpdateDate;
        this.imgUrl = imgUrl;
        this.categories = categories;
    }

    public Article(String title, String content, Long userId, String imgUrl) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.imgUrl = imgUrl;
    }

    public Article() {}

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", likes=" + likes +
                ", lastUpdateDate=" + lastUpdateDate +
                ", imgUrl='" + imgUrl + '\'' +
                ", categories=" + categories +
                '}';
    }
}
