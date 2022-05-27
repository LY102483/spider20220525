package online.liuyang1024.POJO;

/**
 * Create by LiuYang on 2022/5/26 01:38
 */
public class YongYao {
    private String title;//标题
    private String Date;//日期
    private String content;//文章内容
    private String aHref;//文章链接

    @Override
    public String toString() {
        return "YongYao{" +
                "title='" + title + '\'' +
                ", Date='" + Date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getaHref() {
        return aHref;
    }

    public void setaHref(String aHref) {
        this.aHref = aHref;
    }

    public YongYao(String title, String date, String content, String aHref) {
        this.title = title;
        Date = date;
        this.content = content;
        this.aHref = aHref;
    }
}
