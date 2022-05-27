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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Create by LiuYang on 2022/5/24 23:30
 */

@Controller
@RequestMapping("/manxingbing")
public class ManXingBingSpider {
    /**
     * pageIndex:传入页码
     * 获取传入页码的内容
     */
    @RequestMapping("/getContainer")
    @ResponseBody
    public String getContainer(int pageIndex) throws Exception {
        pageIndex*=10;
        //读取url,返回json串
        StringBuilder json = new StringBuilder();
        URL oracle = new URL("http://www.cn-healthcare.com/freezingapi/api/article/articlelist?data={\"start\":\"4\",\"size\":\"10\",\"wmstart\":\""+pageIndex+"\",\"flag\":\"2\",\"arctype\":\"1022\"}");
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine = null;
        while((inputLine = in.readLine()) != null){
            json.append(inputLine);
        }
        in.close();
        String res=json.toString();
        return res;
    }
}
