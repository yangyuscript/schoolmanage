package com.sms.schoolmanage.controller;

import com.sms.schoolmanage.constants.WebConstant;
import com.sms.schoolmanage.domain.Course;
import com.sms.schoolmanage.domain.Exam;
import com.sms.schoolmanage.domain.Notice;
import com.sms.schoolmanage.parseutils.SpiderUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {
    @RequestMapping(value = "/notices",method = RequestMethod.GET)
    public Map<String,Object> getNotices(@RequestParam("userName")String userName,@RequestParam("password")String password){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login(userName,password, WebConstant.GET_PASSWORD);
        List<Notice> notices = spiderUtil.getNotices();
        Map<String, Object> map = new HashMap<>();
        map.put("notices",notices);
        return map;
    }

    @RequestMapping(value = "/courseTable",method = RequestMethod.GET)
    public Map<String,Object> getCourseTable(@RequestParam("bh")String bh,@RequestParam("xq")String xq){
        SpiderUtil spiderUtil = new SpiderUtil();
        List<Course> courses = spiderUtil.getCourseTable(bh,xq);
        Map<String, Object> map = new HashMap<>();
        map.put("courses",courses);
        return map;
    }

    @RequestMapping(value = "/examSetting",method = RequestMethod.GET)
    public Map<String,Object> getExamSetting(@RequestParam("userName")String userName,@RequestParam("password")String password,@RequestParam("lb")String lb,@RequestParam("xq")String xq,@RequestParam("bh")String bh){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login(userName,password,WebConstant.GET_PASSWORD);
        List<Exam> exams = spiderUtil.getExams(lb,xq,bh);
        Map<String, Object> map = new HashMap<>();
        map.put("exams",exams);
        return map;
    }

}
