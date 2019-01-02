package com.sms.schoolmanage.controller;

import com.sms.schoolmanage.domain.Course;
import com.sms.schoolmanage.domain.Notice;
import com.sms.schoolmanage.parseutils.SpiderUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
    @RequestMapping(value = "/notices",method = RequestMethod.GET)
    public Map<String,Object> getNotices(){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login("1509660112","048717","登陆");
        List<Notice> notices = spiderUtil.getNotices();
        Map<String, Object> map = new HashMap<>();
        map.put("notices",notices);
        return map;
    }

    @RequestMapping(value = "/courseTable",method = RequestMethod.GET)
    public Map<String,Object> getCourseTable(){
        SpiderUtil spiderUtil = new SpiderUtil();
        List<Course> courses = spiderUtil.getCourseTable();
        Map<String, Object> map = new HashMap<>();
        map.put("courses",courses);
        return map;
    }

}
