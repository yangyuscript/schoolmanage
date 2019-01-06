package com.sms.schoolmanage.controller;

import com.sms.schoolmanage.constants.WebConstant;
import com.sms.schoolmanage.domain.Course;
import com.sms.schoolmanage.domain.Exam;
import com.sms.schoolmanage.domain.Notice;
import com.sms.schoolmanage.domain.Score;
import com.sms.schoolmanage.parseutils.SpiderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Map<String,Object> login(@RequestParam("userName") String userName, @RequestParam("password") String password){
        log.error(WebConstant.LOG_ERROR_STR,userName);
        log.error(WebConstant.LOG_ERROR_STR,password);
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        boolean flag = spiderUtil.login(userName,password, WebConstant.GET_PASSWORD);
        Map<String, Object> map = new HashMap<>();
        if(flag){
            //登录成功
            map.put("result",WebConstant.OK);
        }else{
            //登录失败
            map.put("result",WebConstant.NO);
        }
        return map;
    }

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

    @RequestMapping(value = "/scores",method = RequestMethod.GET)
    public Map<String,Object> getExamSetting(@RequestParam("userName")String userName,@RequestParam("password")String password){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login(userName,password,WebConstant.GET_PASSWORD);
        List<Score> scores = spiderUtil.getScores();
        Map<String, Object> map = new HashMap<>();
        map.put("scores",scores);
        return map;
    }

}
