package com.atguigu.tools.utils;

import com.atguigu.tools.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HtmlParseUtil {

    public List<Content> parseJD(String keyword) throws  Exception {
        String url = "https://search.jd.com/Search?keyword=" + keyword + "&enc=utf-8"; //处理中文查询问题
        //解析网页
        Document document = Jsoup.parse(new URL(new String(url.getBytes(), "utf-8")), 30000);
        Element element = document.getElementById("J_goodsList");
//        System.out.println(element.html());
        //获取所有的li标签
        Elements elements = element.getElementsByTag("li");
        ArrayList<Content> contentList = new ArrayList<>();
        for (Element el : elements) {
//            String img = el.getElementsByTag("p-img").eq(0).attr("src");
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img"); //获取懒加载的图片
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            contentList.add(new Content(img, price, title));
        }
        return contentList;
    }



//
//    public static void main(String[] args) throws Exception {
//        List<Content> list = parseJD("心理学");
//        list.forEach(System.out::println);
//    }
}
