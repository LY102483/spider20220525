package online.liuyang1024.POJO;

/**
 * Create by LiuYang on 2022/5/26 00:57
 */
public class JianKang {
    private String episode;//期数
    private String title;//标题
    private String img;//图片
    private String description;//简介
    private String aHref;//链接
    private String content;//正文内容


    public JianKang(String episode, String title, String img, String description, String aHref, String content) {
        this.episode = episode;
        this.title = title;
        this.img = img;
        this.description = description;
        this.aHref = aHref;
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    public JianKang() {
    }

    @Override
    public String toString() {
        return "JianKang{" +
                "episode='" + episode + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", aHref='" + aHref + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
