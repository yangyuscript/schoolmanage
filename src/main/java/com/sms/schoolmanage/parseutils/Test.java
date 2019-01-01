package com.sms.schoolmanage.parseutils;

import com.sms.schoolmanage.domain.Notice;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login("1509660112","048717","登陆");
        /*List<Notice> notices = spiderUtil.getNotices();
        System.out.println(notices.size()+notices.toString());*/
        spiderUtil.getCourseTable();
    }
}
