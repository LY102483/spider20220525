package online.liuyang1024.test;

import online.liuyang1024.POJO.JianKang;
import online.liuyang1024.POJO.YongYao;
import online.liuyang1024.spider.JianKangSpider;
import online.liuyang1024.spider.ManXingBingSpider;
import online.liuyang1024.spider.YiqingSpider;
import online.liuyang1024.spider.YongYaoSpider;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.util.List;

/**
 * Create by LiuYang on 2022/5/26 01:05
 */
public class Test {
    public static void main(String[] args) throws Exception {
        YongYaoSpider yongYaoSpider=new YongYaoSpider();
        System.out.println(yongYaoSpider.getContainer(2));
    }
}
