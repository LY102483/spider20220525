package online.liuyang1024.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import online.liuyang1024.POJO.JianKang;
import online.liuyang1024.POJO.YiQing;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Create by LiuYang on 2022/5/24 23:30
 */

@Controller
@RequestMapping("/jiankang")
public class JianKangSpider {
    @RequestMapping("/getTotalPage")
    @ResponseBody
    public int getTotalPage() throws IOException, DocumentException {
        // 实例化Web客户端
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false); // 取消 CSS 支持 ✔
        webClient.getOptions().setJavaScriptEnabled(false); // 取消 JavaScript支持 ✔
        int totalPage=0;
        try {
            HtmlPage page = webClient.getPage("http://m.39.net/news/xinzhi/"); // 解析获取页面
            List<HtmlElement> spanList=page.getByXPath("//div[@class='mPage']");//将符合要求的元素存入List集合
            //返回回来的是一个字符串openPage(页码),去掉字符串中的非数字部分并返回
            String REGEX = "[^0-9]";
            String ticketStr = spanList.get(0).asText().split("/")[1];
            totalPage = Integer.parseInt(Pattern.compile(REGEX).matcher(ticketStr).replaceAll("").trim());
        } catch (FailingHttpStatusCodeException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            webClient.close(); // 关闭客户端，释放内存
        }
        return totalPage;
    }

    /**
     * pageIndex:传入页码
     * 获取传入页码的内容
     */
    @RequestMapping("/getContainer")
    @ResponseBody
    public List<JianKang> getContainer(int pageIndex) throws Exception {
        pageIndex=pageIndex-1;
        List<JianKang> res=new ArrayList<>();
        // 实例化Web客户端、①模拟 Chrome 浏览器 ✔ 、②使用代理IP ×
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false); // 取消 CSS 支持 ✔
        webClient.getOptions().setJavaScriptEnabled(false); // 取消 JavaScript支持 ✔
        try {
            HtmlPage page = webClient.getPage("http://m.39.net/news/xinzhi/index_"+pageIndex+".html"); // 解析获取页面
            List<HtmlElement> liList=page.getByXPath("//ul[@class='mList xzList']/li");//将符合要求的元素存入List集合
            for (HtmlElement element : liList) {
                String episode=element.getElementsByTagName("b").get(0).asText();//期数
                String title=element.getElementsByTagName("a").get(0).getAttribute("title");//标题
                String img=element.getElementsByTagName("img").get(0).getAttribute("src");//图片链接
                String description=element.getElementsByTagName("p").get(0).asText();//简介
                String aHref=element.getElementsByTagName("a").get(0).getAttribute("href");//文章链接
                // 获取链接内的正文
                String content="";
                HtmlPage contentPage = webClient.getPage(aHref);
                List<HtmlElement> pList=contentPage.getByXPath("//div[@id='mArt_ps']");
                // 放进去的是text格式
                for (HtmlElement pElement : pList) {
                    content+=pElement.asText();
                }
                JianKang jianKang=new JianKang(episode,title,img,description,aHref,content);
                res.add(jianKang);
            }
        } catch (FailingHttpStatusCodeException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            webClient.close(); // 关闭客户端，释放内存
        }
        return res;
    }
}
