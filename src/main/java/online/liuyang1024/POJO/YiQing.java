package online.liuyang1024.POJO;

/**
 * Create by LiuYang on 2022/5/25 01:01
 */
public class YiQing {

    private String tag;
    private String title;
    private String date;
    private String aHref;
    private String content;

    public YiQing(String tag, String title, String date, String aHref, String content) {
        this.tag = tag;
        this.title = title;
        this.date = date;
        this.aHref = aHref;
        this.content = content;
    }

    public YiQing() {
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getaHref() {
        return aHref;
    }

    public void setaHref(String aHref) {
        this.aHref = aHref;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
