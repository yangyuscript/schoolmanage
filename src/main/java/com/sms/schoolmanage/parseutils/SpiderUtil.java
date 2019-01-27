package com.sms.schoolmanage.parseutils;

import com.sms.schoolmanage.constants.WebConstant;
import com.sms.schoolmanage.domain.*;
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
    private Map<String, String> cookies = new HashMap<>();
    private Connection connection;
    private Connection.Response response;
    private Document document;
    //单双周
    private Integer week;
    private String term;


    public void init() {
        try {
            //获取连接
            connection = Jsoup.connect(WebConstant.WEBSITE_INDEX_URL);
            //配置模拟浏览器
            connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            response = connection.timeout(30000).execute();
            //保存Cookies
            cookies = response.cookies();
            // 将响应转换为Dom树，以便获取__VIEWSTATE
            document = Jsoup.parse(response.body());
            int flag = 0;
            for (Element element : document.getElementsByTag("input")) {
                if (element.attr("name").equals("__VIEWSTATE")) {
                    viewState = element.val();
                    flag++;
                }
                if (element.attr("name").equals("__VIEWSTATEGENERATOR")) {
                    viewStateGenerator = element.val();
                    flag++;
                }
                if (flag >= 2) {
                    break;
                }
            }
        } catch (IOException ex) {
            log.error(WebConstant.LOG_ERROR_STR, "爬虫初始化失败");
            ex.printStackTrace();
        }
    }

    public Map<String,Object> login(String userName, String password, String getPassword) {
        boolean flag = false;
        Map<String,Object> result = new HashMap<>();
        //填充post数据
        Map<String, String> datas = new HashMap<>();
        datas.put("__VIEWSTATE", viewState);
        datas.put("__VIEWSTATEGENERATOR", viewStateGenerator);
        datas.put("UserName", userName);
        datas.put("Password", password);
        datas.put("getpassword", getPassword);

        try {
            connection = Jsoup.connect(WebConstant.WEBSITE_LOGIN_URL);
            connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            // 设置cookie和post上面的map数据
            response = connection.postDataCharset("UTF-8").ignoreContentType(true).method(Connection.Method.POST)
                    .data(datas).cookies(cookies).execute();
            //保存Cookies
            cookies = response.cookies();

            //登录后跳转到主页面
            getConnection(WebConstant.WEBSITE_LOGINED_INDEX_URL);

            log.error(WebConstant.LOG_ERROR_STR, document.title().toString());
            if (document.title().toString().trim().contains("南京理工大学泰州科技学院--教务管理信息系统")) {
                log.error(WebConstant.LOG_ERROR_STR, "登录成功!");
                String content = document.getElementsByClass("left").get(0).text();
                log.error(WebConstant.LOG_ERROR_STR, content);
                String stuName = content.split(" ")[1].trim();
                log.error(WebConstant.LOG_ERROR_STR, stuName);
                String text = document.getElementById("FormView1").getElementsByTag("td").text().trim();
                String s = document.getElementById("FormView1").getElementsByTag("td").text().trim().split("\\|")[1];
                this.week = Integer.valueOf(s.substring(1,s.length()-1));
                log.error(WebConstant.LOG_ERROR_STR, "第"+this.week+"周");
                this.term = text.split(" ")[1];
                log.error(WebConstant.LOG_ERROR_STR, "第"+this.term+"学期");
                flag = true;
            } else {
                log.error(WebConstant.LOG_ERROR_STR, "登录失败!");
                flag = false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            flag = false;
        }
        result.put("flag",flag);
        result.put("week",this.week);
        result.put("term",this.term);
        return result;
    }

    public List<Notice> getNotices() {
        getConnection(WebConstant.WEBSITE_NOTICE_URL);
        Element panel1 = document.getElementById("Panel1");
        Elements elementsByClass = panel1.getElementsByClass("dg1-item");
        List<Notice> notices = new LinkedList<>();
        for (Element dg1_item : elementsByClass) {
            Notice notice = new Notice();
            try {
                String href = dg1_item.getElementsByTag("a").get(0).attr("href");
                href = href.replace("\\", "/").replace(" ", "");
                String title = dg1_item.getElementsByTag("a").get(0).text();
                String time = dg1_item.getElementsByTag("td").get(1).text();
                getConnection(WebConstant.WEBSITE_INDEX_URL + href);
                //String content = document.getElementById("listcolumn").html().replace("\"","'");
                String content = document.getElementsByClass("ziti14").html().replace("\"","'");
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

    public List<Course> getCourseTable(String bh,String xq) {
        getConnection(String.format(WebConstant.WEBSITE_CLASS_COURSES_TABLE_URL, bh, xq));
        List<Course> courses = new LinkedList<>();
        Elements elements = document.getElementById("GVkb").getElementsByClass("dg1-item");
        for (int i = 0, len = elements.size(); i < len; i++) {
            Elements tds = elements.get(i).getElementsByTag("td");
            for (int j = 1, len2 = tds.size(); j < len2; j++) {
                String kcmc = tds.get(j).html();
                if (!kcmc.equals("&nbsp;")) {
                    Course course = new Course();
                    String[] kcmc_array = kcmc.split("/");
                    if(kcmc_array.length>1){
                        if(this.week%2==0){
                            kcmc = kcmc_array[1];
                        }else{
                            kcmc = kcmc_array[0];
                        }
                    }
                    course.setKcmc(kcmc);
                    course.setXqj(j);
                    course.setSkcd(2);
                    String skjc = tds.get(0).text();
                    if (skjc.length() > 2) {
                        course.setSkjc(Integer.parseInt(skjc.substring(0, 1)));
                    } else {
                        course.setSkjc(Integer.parseInt(skjc));
                    }
                    courses.add(course);
                }
            }
            i++;
        }
        return courses;
    }

    public List<Exam> getExams(String lb, String xq, String bh) {
        List<Exam> examList = new LinkedList<>();
        getConnection(String.format(WebConstant.WEBSITE_EXAMS_SETTING_URL, lb, xq, WebConstant.WHFS, bh));
        Element element = document.getElementById("GVkssj");
        if (null != element) {
            Elements trs = element.getElementsByClass("dg1-item");
            for (int i = 0, len = trs.size(); i < len; i++) {
                Elements tds = trs.get(i).getElementsByTag("td");
                Exam exam = new Exam();
                exam.setSchool(tds.get(0).html().trim().equals("&nbsp;")?"待定":tds.get(0).text());
                exam.setCourseCode(tds.get(1).html().trim().equals("&nbsp;")?"待定":tds.get(1).text());
                exam.setCourseName(tds.get(2).html().trim().equals("&nbsp;")?"待定":tds.get(2).text());
                exam.setClassName(tds.get(3).html().trim().equals("&nbsp;")?"待定":tds.get(3).text());
                exam.setExamTime(tds.get(4).html().trim().equals("&nbsp;")?"待定":tds.get(4).text());
                exam.setExamAddress(tds.get(5).html().trim().equals("&nbsp;")?"待定":tds.get(5).text());
                exam.setMonitorOne(tds.get(6).html().trim().equals("&nbsp;")?"待定":tds.get(6).text());
                exam.setMonitorTwo(tds.get(7).html().trim().equals("&nbsp;")?"待定":tds.get(7).text());
                exam.setRemark(tds.get(8).html().trim().equals("&nbsp;")?"无":tds.get(8).text());
                exam.setExamWeek(tds.get(9).html().trim().equals("&nbsp;")?"待定":tds.get(9).text());
                exam.setWeek(tds.get(10).html().trim().equals("&nbsp;")?"待定":tds.get(10).text());
                exam.setSectionOne(tds.get(11).html().trim().equals("&nbsp;")?"待定":tds.get(11).text());
                exam.setSectionTwo(tds.get(12).html().trim().equals("&nbsp;")?"待定":tds.get(12).text());
                examList.add(exam);
            }
        }

        return examList;
    }

    public List<Score> getScores(){
        List<Score> scoreList = new LinkedList<>();
        getConnection(WebConstant.WEBSITE_SCORE_URL);
        String pjjd = document.getElementById("Txtjd").val();
        Elements trs = document.getElementById("gvcj1").getElementsByClass("dg1-item");
        for (Element tr:trs) {
            Elements tds = tr.getElementsByTag("td");
            Score score = new Score();
            score.setXh(tds.get(0).text());
            score.setXm(tds.get(1).text());
            score.setXq(tds.get(2).text());
            score.setKcmc(tds.get(3).text());
            score.setLb(tds.get(4).text());
            score.setXf(tds.get(5).text());
            score.setCj(tds.get(6).text());
            score.setKsxz(tds.get(7).text());
            score.setJd(tds.get(8).text());
            score.setKcdm(tds.get(9).text());
            score.setXs(tds.get(10).text());
            score.setPjjd(pjjd);
            scoreList.add(score);
        }
        return scoreList;
    }

    public Student getStudentInfo(){
        getConnection(WebConstant.WEBSITE_PERSONAL_INFO_URL);
        Student student = new Student();
        student.setBh(document.getElementById("Txtbh").val());
        student.setBj(document.getElementById("Txtbj").val());
        student.setXh(document.getElementById("Txtxh").val());
        student.setXm(document.getElementById("Txtxm").val());
        student.setXb(document.getElementById("Txtxb").val());
        student.setCsrq(document.getElementById("Txtcsny").val());
        student.setXz(document.getElementById("Txtxz").val());
        student.setYxmc(document.getElementById("Txtxbmc").val());
        student.setJg(document.getElementById("Txtjg").val());
        student.setZymc(document.getElementById("Txtzymc").val());
        student.setMz(document.getElementById("Txtmzm").val());
        student.setZydm(document.getElementById("Txtzydm").val());
        student.setSylb(document.getElementById("Txtsylb").val());
        student.setSfzh(document.getElementById("Txtsfzh").val());
        student.setNj(document.getElementById("Txtnj").val());
        student.setZzmm(document.getElementById("Txtzzmm").val());
        student.setRxrq(document.getElementById("Txtrxrq").val());
        student.setZkzh(document.getElementById("Txtzkzh").val());
        student.setZxqxstc(document.getElementById("Txtkstc").val());
        student.setGkcj(document.getElementById("Txtgkcj").val());
        student.setGkgkcj(document.getElementById("Txtkccj").val());
        student.setRxqjcqk(document.getElementById("Txtjlcf").val());
        student.setJtlxr(document.getElementById("Txtlxr").val());
        student.setJtdh(document.getElementById("Txtlxdh").val());
        student.setJtdz(document.getElementById("Txtjtdz").val());
        student.setYb(document.getElementById("Txtcode").val());
        student.setWxh(document.getElementById("Txtwxh").val());
        student.setBrdh(document.getElementById("Txttel2").val());
        student.setHczdz(document.getElementById("Txtccqj").val());
        student.setRxzp(WebConstant.WEBSITE_INDEX_URL+document.getElementById("Image1").attr("src").substring(3));
        student.setZxzp(WebConstant.WEBSITE_INDEX_URL+document.getElementById("Image2").attr("src").substring(3));
        student.setByzp(WebConstant.WEBSITE_INDEX_URL+document.getElementById("Image3").attr("src").substring(3));
        return student;
    }

    public List<CourseDetail> getCourseDetails(String bh,String xq){
        getConnection(String.format(WebConstant.WEBSITE_CLASS_COURSES_TABLE_URL, bh, xq));
        List<CourseDetail> cdList = new LinkedList<>();
        Elements elements = document.getElementById("GVkbk").getElementsByClass("dg1-item");
        for (int i = 0, len = elements.size(); i < len; i++) {
            Elements tds = elements.get(i).getElementsByTag("td");
            CourseDetail cd = new CourseDetail();
            cd.setCdid(0);
            cd.setCid(0);
            cd.setBj(tds.get(0).text());
            cd.setKch(tds.get(1).text());
            cd.setKcmc(tds.get(2).text());
            cd.setKclb(tds.get(3).text());
            cd.setKh(tds.get(4).text());
            cd.setZxs(tds.get(5).text());
            cd.setXf(tds.get(6).text());
            cd.setSkjs(tds.get(7).text());
            cd.setSjzc(tds.get(8).text());
            cdList.add(cd);
        }
        return cdList;
    }

    public List<LevelTest> getLevelTest(){
        getConnection(WebConstant.WEBSITE_LEVEL_TEST_URL);
        List<LevelTest> ltList = new LinkedList<>();
        Elements elements = document.getElementById("GVcet").getElementsByClass("dg1-item");
        for (int i = 0, len = elements.size(); i < len; i++) {
            Elements tds = elements.get(i).getElementsByTag("td");
            LevelTest lt = new LevelTest();
            lt.setLtid(0);
            lt.setBj(tds.get(0).text());
            lt.setXh(tds.get(1).text());
            lt.setXm(tds.get(2).text());
            lt.setKsny(tds.get(3).text());
            lt.setYzdm(tds.get(4).text());
            lt.setYzmc(tds.get(5).text());
            lt.setZkzhm(tds.get(6).text());
            lt.setKscj(tds.get(7).text());
            ltList.add(lt);
        }
        return ltList;
    }


    //共用方法
    private void getConnection(String url) {
        try {
            connection = Jsoup.connect(url);
            connection.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            connection.postDataCharset("UTF-8").ignoreContentType(true).method(Connection.Method.GET).cookies(cookies);
            response = connection.execute();
            //cookies = response.cookies();   //此句话会将cookies设置为空，不能随便使用
            document = Jsoup.parse(response.body());
        } catch (IOException e) {
            e.printStackTrace();
            log.error(WebConstant.LOG_ERROR_STR, url + " 连接失败");
        }
    }
}
