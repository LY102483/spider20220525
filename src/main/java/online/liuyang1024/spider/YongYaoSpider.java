package online.liuyang1024.spider;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import online.liuyang1024.POJO.YiQing;
import online.liuyang1024.POJO.YongYao;
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
@RequestMapping("/yongyao")
public class YongYaoSpider {
    @RequestMapping("/getTotalPage")
    @ResponseBody
    public int getTotalPage() throws IOException, DocumentException, InterruptedException {
        // 实例化Web客户端
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false); // 取消 CSS 支持 ✔
        webClient.getOptions().setJavaScriptEnabled(true); //对JavaScript支持 ✔
        int totalInfo=0;//数据总条数
        try {
            HtmlPage page = webClient.getPage("http://news.yongyao.net/pages/newsmore.aspx?newsmc="); // 解析获取页面
            Thread.sleep(1000);
            List<HtmlElement> spanList=page.getByXPath("//div[@id='vod_message_pager']/li");//获取符合要求的元素
            // 返回回来的是一个字符串去掉字符串中的非数字部分并返回
            String REGEX = "[^0-9]";
            String ticketStr = spanList.get(2).asText();//只获取最终显示的内容
            totalInfo = Integer.parseInt(Pattern.compile(REGEX).matcher(ticketStr).replaceAll("").trim());
        } catch (FailingHttpStatusCodeException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            webClient.close(); // 关闭客户端，释放内存
        }
        //总条数等于数据总数/25
        return totalInfo/25;
    }

    /**
     * pageIndex:传入页码
     * 获取传入页码的内容
     */
    @RequestMapping("/getContainer")
    @ResponseBody
    public List<YongYao> getContainer(int pageIndex) throws Exception {
        List<YongYao> res=new ArrayList<>();
        // 实例化Web客户端、①模拟 Chrome 浏览器 ✔ 、②使用代理IP ✔
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false); // 取消 CSS 支持 ✔
        webClient.getOptions().setJavaScriptEnabled(true); // 对JavaScript支持 ✔
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//请求发生错误时是否抛异常
        try {
            HtmlPage page = webClient.getPage("http://news.yongyao.net/pages/newsmore.aspx?newsmc="); // 解析获取页面
            Thread.sleep(1000);//等待JS加载
            List<HtmlElement> liList=page.getByXPath("//div[@id='vod_message_pager']/li/input");//获取页码输入框
            liList.get(0).setAttribute("value", pageIndex+"");//设置页码
            List<HtmlElement>  goToPage= page.getByXPath("//div[@id='vod_message_pager']/li/a");//获取跳转页面的超链接
            goToPage.get(0).click();//点击超链接
            Thread.sleep(1000);//等待JS加载
            //已经跳转到新页面啦！！！
            //获取标题和日期
            List<HtmlElement> titleList=page.getByXPath("//div[@class='main_lb_center_nr1_left']/ul/li");//获取标题
            List<HtmlElement> dateList=page.getByXPath("//div[@class='main_lb_center_nr1_right']/ul/li");//获取文章发布日期
            //创建对象，存入list
            int cnt=0;//因为原网页每5条数据有一条空数据，爬虫遇到会抛异常，所以用计数手段进行跳过
            for(int i=0;i<titleList.size();i++){
                if(cnt==5){
                    //满5条忽略这条数据
                    cnt=0;
                    continue;
                }else{
                    String title=titleList.get(i).asText();//标题
                    String date=dateList.get(i).asText();//发布时间
                    String aHref="http://news.yongyao.net"+titleList.get(i).getElementsByTagName("a").get(0).getAttribute("href");//文章链接
                    HtmlPage contentPage = webClient.getPage(aHref); // 解析文章内容页面
                    List<HtmlElement> pList=contentPage.getByXPath("//div[@class='main_zx_left_xx_nr']");//获取文章发布日期
                    String content=pList.get(0).asText();//文章内容
                    YongYao yongYao = new YongYao(title, date, content, aHref);
                    res.add(yongYao);
                    cnt++;
                }
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
