package com.sms.schoolmanage.constants;

public class WebConstant {
    public static String WEBSITE_INDEX_URL = "http://jwgl.nustti.edu.cn/";

    public static String WEBSITE_LOGIN_URL = "http://jwgl.nustti.edu.cn/login_new.aspx";

    public static String WEBSITE_LOGINED_INDEX_URL = "http://jwgl.nustti.edu.cn/index3.aspx";

    /**
     * 获取通知（需登录）
     */
    public static String WEBSITE_NOTICE_URL = "http://jwgl.nustti.edu.cn/officetable6.aspx";
    /**
     * 班级课表
     * 班级课表（通过学生编号和学期直接获取，不需要登录）http://jwgl.nustti.edu.cn/web_jxrw/cx_kb_bjkb_bj.aspx?xsbh=16096601&xq=18-19-1
     */
    public static String WEBSITE_CLASS_COURSES_TABLE_URL="http://jwgl.nustti.edu.cn/web_jxrw/cx_kb_bjkb_bj.aspx?xsbh=%s&xq=%s";

    /**
     * 课表查询页面（需登录）
     */
    public static String WEBSITE_SEARCH_CLASS_COURSES_TABLE_URL="http://jwgl.nustti.edu.cn/web_jxrw/cx_kb_bjxzall.aspx";

    /**
     * 考试安排查询(需登录)http://jwgl.nustti.edu.cn/web_ksgl/ksgl_ksapcx_sj.aspx?lb=%E5%AD%A6%E5%88%86%E5%88%B6%E6%9C%9F%E6%9C%AB%E8%80%83%E8%AF%95&xq=18-19-1&whfs=%E6%9F%A5%E8%AF%A2%E5%AD%A6%E7%94%9F%E9%80%89%E8%AF%BE%E8%AF%BE%E7%A8%8B%E8%80%83%E8%AF%95%E5%AE%89%E6%8E%92&kssj=&bh=1509660112&ksz=0
     *  lb：不及格课程补考  学分制期末考试
     */
    public static String WEBSITE_EXAMS_SETTING_URL="http://jwgl.nustti.edu.cn/web_ksgl/ksgl_ksapcx_sj.aspx?lb=%s&xq=%s&whfs=%s&kssj=&bh=%s&ksz=0";

    /**
     *  获取考试成绩（需登录）
     */
    public static String WEBSITE_SCORE_URL="http://jwgl.nustti.edu.cn/web_cjgl/cx_cj_xh.aspx";


    /**
     * 获取学生个人信息（需登录）
     */
    public static String WEBSITE_PERSONAL_INFO_URL="http://jwgl.nustti.edu.cn/web_xjgl/xjgl_wh_tj_xsxx.aspx";

    /**
     * 等级考试成绩
     */
    public static String WEBSITE_LEVEL_TEST_URL="http://jwgl.nustti.edu.cn/web_cjgl/cx_cetjsj_xscj_xh.aspx";

    /**
     * 学校校历
     */
    public static String WEBSITE_SCHOOL_DATE_PIC_URL="http://www.nustti.edu.cn//ueditor/php/upload/image/20190216/1550281719898675.png";




    public static String LOG_ERROR_STR = "*-----------{}-----------*";

    public static String WHFS = "查询学生选课课程考试安排";

    public static String GET_PASSWORD = "登陆";

    public static String ANORMAL_EXAMS = "不及格课程补考";

    public static String NORMAL_EXAMS = "学分制期末考试";

    public static String OK = "ok";

    public static String NO = "no";
}
