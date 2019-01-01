package com.sms.schoolmanage.constants;

public class WebConstant {
    public static String WEBSITE_INDEX_URL = "http://jwgl.nustti.edu.cn/";

    public static String WEBSITE_LOGIN_URL = "http://jwgl.nustti.edu.cn/login_new.aspx";

    public static String WEBSITE_LOGINED_INDEX_URL = "http://jwgl.nustti.edu.cn/index3.aspx";

    /**
     *获取通知
     */
    public static String WEBSITE_NOTICE_URL = "http://jwgl.nustti.edu.cn/officetable6.aspx";
    /**
     * 班级课表
     * 班级课表（通过学生编号和学期直接获取，不需要登录）http://jwgl.nustti.edu.cn/web_jxrw/cx_kb_bjkb_bj.aspx?xsbh=16096601&xq=18-19-1
     */
    public static String WEBSITE_CLASS_COURSES_TABLE_URL="http://jwgl.nustti.edu.cn/web_jxrw/cx_kb_bjkb_bj.aspx?xsbh=%s&xq=%s";

    /**
     * 课表查询页面
     */
    public static String WEBSITE_SEARCH_CLASS_COURSES_TABLE_URL="http://jwgl.nustti.edu.cn/web_jxrw/cx_kb_bjxzall.aspx";

    public static String LOG_ERROR_STR = "error-----------{}-----------error";
}
