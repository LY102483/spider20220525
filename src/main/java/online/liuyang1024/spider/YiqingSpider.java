package online.liuyang1024.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
@RequestMapping("/yiqing")
public class YiqingSpider {
    @RequestMapping("/getTotalPage")
    @ResponseBody
    public int getTotalPage() throws IOException, DocumentException {
        // 实例化Web客户端
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false); // 取消 CSS 支持 ✔
        webClient.getOptions().setJavaScriptEnabled(false); // 取消 JavaScript支持 ✔
        int totalPage=0;
        try {
            HtmlPage page = webClient.getPage("https://www.ithc.cn/topic/00003.html"); // 解析获取页面
            List<HtmlElement> spanList=page.getByXPath("//input[@class='file1'][4]");//将符合要求的元素存入List集合
            //返回回来的是一个字符串openPage(页码),去掉字符串中的非数字部分并返回
            String REGEX = "[^0-9]";
            String ticketStr = spanList.get(0).getOnClickAttribute();
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
        //因为原网页每页30条数据，假设微信小程序端每页显示10条，则微信小程序上的总页数应该为原网页总页数的三倍
        //原网页的最后一页不进行爬取操作
        return (totalPage-1)*3;
    }

    /**
     * pageIndex:传入页码
     * 获取传入页码的内容
     */
    @RequestMapping("/getContainer")
    @ResponseBody
    public List<YiQing> getContainer(int pageIndex) throws Exception {
        // 原网页一页分成了3页
        int truePage=(pageIndex+2)/3;
        //属于目标网页的第几段  1表示第一段，2表示第2段，0表示第三段
        int cnt=pageIndex%3;
        if(cnt==0){
            cnt=3;
        }


        List<YiQing> res=new ArrayList<>();
        // 实例化Web客户端、①模拟 Chrome 浏览器 ✔ 、②使用代理IP ✔
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false); // 取消 CSS 支持 ✔
        webClient.getOptions().setJavaScriptEnabled(false); // 取消 JavaScript支持 ✔
        try {
            HtmlPage page = webClient.getPage("https://www.ithc.cn/topic/00003.html?cp="+truePage); // 解析获取页面
            List<HtmlElement> liList=page.getByXPath("//ul[@class='topic-article-list']/li");//将符合要求的元素存入List集合
            for(int i=(cnt-1)*10;i<cnt*10;i++){
                HtmlElement element=liList.get(i);
            // for (HtmlElement element : liList) {
                String tag=element.getElementsByTagName("span").get(0).asText();//标签
                String title=element.getElementsByTagName("a").get(0).asText();//标题
                String date=element.getElementsByTagName("span").get(1).asText();//发布时间
                String href="https://www.ithc.cn"+element.getElementsByTagName("a").get(0).getAttribute("href");//链接
                //获取链接内的正文
                String content="";
                HtmlPage contentPage = webClient.getPage(href);
                List<HtmlElement> pList=contentPage.getByXPath("//div[@class='article-content']/p");
                //放进去的是xml格式
                for (HtmlElement pElement : pList) {
                    content+=pElement.asText();
                }
                YiQing yiQing=new YiQing(tag,title,date,href,content);
                res.add(yiQing);
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
