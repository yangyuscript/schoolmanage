package com.sms.schoolmanage.parseutils;

import com.sms.schoolmanage.constants.WebConstant;
import com.sms.schoolmanage.domain.Notice;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class SpiderUtil {
    private final Logger log = LoggerFactory.getLogger(this.getClass());



    private String viewState = new String();
    private String viewStateGenerator = new String();
    private Map<String,String> cookies = new HashMap<>();
    private Connection connection;
    private Connection.Response response;
    private Document document;



    public void init(){
        try{
            //获取连接
            connection = Jsoup.connect(WebConstant.WEBSITE_INDEX_URL);
            //配置模拟浏览器
            connection.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            response = connection.timeout(30000).execute();
            //保存Cookies
            cookies = response.cookies();
            // 将响应转换为Dom树，以便获取__VIEWSTATE
            document = Jsoup.parse(response.body());
            int flag = 0;
            for(Element element:document.getElementsByTag("input")) {
                if (element.attr("name").equals("__VIEWSTATE")) {
                    viewState = element.val();
                    flag++;
                }
                if (element.attr("name").equals("__VIEWSTATEGENERATOR")) {
                    viewStateGenerator = element.val();
                    flag++;
                }
                if(flag>=2){
                    break;
                }
            }
        }catch (IOException ex){
            log.error(WebConstant.LOG_ERROR_STR,"爬虫初始化失败");
            ex.printStackTrace();
        }
    }

    public boolean login(String userName,String password,String getPassword){
        boolean flag = false;

        //填充post数据
        Map<String, String> datas = new HashMap<>();
        datas.put("__VIEWSTATE",viewState);
        datas.put("__VIEWSTATEGENERATOR",viewStateGenerator);
        datas.put("UserName",userName);
        datas.put("Password",password);
        datas.put("getpassword",getPassword);

        try{
            connection = Jsoup.connect(WebConstant.WEBSITE_LOGIN_URL);
            connection.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            // 设置cookie和post上面的map数据
            response = connection.postDataCharset("UTF-8").ignoreContentType(true).method(Connection.Method.POST)
                    .data(datas).cookies(cookies).execute();
            //保存Cookies
            cookies = response.cookies();

            //登录后跳转到主页面
            getConnection(WebConstant.WEBSITE_LOGINED_INDEX_URL);

            log.error(WebConstant.LOG_ERROR_STR,document.title().toString());
            if(document.title().toString().trim().contains("南京理工大学泰州科技学院--教务管理信息系统")){
                log.error(WebConstant.LOG_ERROR_STR,"登录成功!");
                String content = document.getElementsByClass("left").get(0).text();
                log.error(WebConstant.LOG_ERROR_STR,content);
                String stuName = content.split(" ")[1].trim();
                log.error(WebConstant.LOG_ERROR_STR,stuName);
                flag = true;
            }else {
                log.error(WebConstant.LOG_ERROR_STR,"登录失败!");
                flag = false;
            }
        }catch (IOException ex){
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public List<Notice> getNotices(){
        getConnection(WebConstant.WEBSITE_NOTICE_URL);
        Element panel1 = document.getElementById("Panel1");
        Elements elementsByClass = panel1.getElementsByClass("dg1-item");
        List<Notice> notices = new LinkedList<>();
        for (Element dg1_item: elementsByClass) {
            Notice notice = new Notice();
            try {
                String href = dg1_item.getElementsByTag("a").get(0).attr("href");
                href = href.replace("\\","/").replace(" ","");
                String title = dg1_item.getElementsByTag("a").get(0).text();
                String time = dg1_item.getElementsByTag("td").get(1).text();
                getConnection(WebConstant.WEBSITE_INDEX_URL+href);
                String content = document.getElementById("listcolumn").html();
                notice.setUrl(href);
                notice.setTitle(title);
                notice.setTime(time);
                notice.setContent(content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            notices.add(notice);
        }
        return notices;
    }

    public void getCourseTable(){
        getConnection(String.format(WebConstant.WEBSITE_CLASS_COURSES_TABLE_URL,"16096601","18-19-1"));
        String html = document.getElementById("GVkb").html();
    }


    //共用方法
    private void getConnection(String url){
        try {
            connection = Jsoup.connect(url);
            connection.header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            connection.postDataCharset("UTF-8").ignoreContentType(true).method(Connection.Method.GET).cookies(cookies).timeout(5000);
            response = connection.execute();
            //cookies = response.cookies();   //此句话会将cookies设置为空，不能随便使用
            document = Jsoup.parse(response.body());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(WebConstant.LOG_ERROR_STR,url+" 连接失败");
        }
    }
}
