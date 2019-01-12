package com.sms.schoolmanage.parseutils;

import com.sms.schoolmanage.domain.Notice;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login("1509660112","048717","登陆");
        /*List<Notice> notices = spiderUtil.getNotices();
        System.out.println(notices.size()+notices.toString());
        System.out.println(spiderUtil.getExams("学分制期末考试","18-19-1","1509660112"));*/
        //System.out.println(spiderUtil.getScores());
        System.out.println(spiderUtil.getStudentInfo());
    }
}
