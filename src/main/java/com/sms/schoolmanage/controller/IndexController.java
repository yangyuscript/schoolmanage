package com.sms.schoolmanage.controller;

import com.sms.schoolmanage.constants.WebConstant;
import com.sms.schoolmanage.domain.*;
import com.sms.schoolmanage.parseutils.DateUtil;
import com.sms.schoolmanage.parseutils.SpiderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
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
        Map<String,Object> result = spiderUtil.login(userName,password, WebConstant.GET_PASSWORD);
        Map<String, Object> map = new HashMap<>();
        if((Boolean)result.get("flag")){
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
    public Map<String,Object> getCourseTable(@RequestParam("userName")String userName,@RequestParam("password")String password,@RequestParam("bh")String bh,@RequestParam("xq")String xq){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login(userName,password,WebConstant.GET_PASSWORD);
        List<Course> courses = spiderUtil.getCourseTable(bh,xq);
        Map<String, Object> map = new HashMap<>();
        map.put("courses",courses);
        return map;
    }

    @RequestMapping(value = "/courseDetail",method = RequestMethod.GET)
    public Map<String,Object> getCourseDetail(@RequestParam("bh")String bh,@RequestParam("xq")String xq){
        SpiderUtil spiderUtil = new SpiderUtil();
        List<CourseDetail> courseDetail = spiderUtil.getCourseDetails(bh,xq);
        Map<String, Object> map = new HashMap<>();
        map.put("courseDetail",courseDetail);
        return map;
    }

    @RequestMapping(value = "/examSetting",method = RequestMethod.GET)
    public Map<String,Object> getExamSetting(@RequestParam("userName")String userName,@RequestParam("password")String password,@RequestParam("lb")String lb,@RequestParam("xq")String xq,@RequestParam("bh")String bh){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login(userName,password,WebConstant.GET_PASSWORD);
        Map<String, Object> map = new HashMap<>();
        if("all".equals(lb)){
            List<Exam> normalExams = spiderUtil.getExams(WebConstant.NORMAL_EXAMS,xq,bh);
            List<Exam> anormalExams = spiderUtil.getExams(WebConstant.ANORMAL_EXAMS,xq,bh);
            map.put("searchType","all");
            map.put("normalExams",normalExams);
            map.put("anormalExams",anormalExams);
            return map;
        }
        List<Exam> exams = spiderUtil.getExams(lb,xq,bh);
        map.put("exams",exams);
        map.put("searchType",lb);
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

    @RequestMapping(value = "/studentInfo",method = RequestMethod.GET)
    public Map<String,Object> studentInfo(@RequestParam("userName")String userName,@RequestParam("password")String password){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login(userName,password,WebConstant.GET_PASSWORD);
        Student student = spiderUtil.getStudentInfo();
        Map<String, Object> map = new HashMap<>();
        map.put("studentInfo",student);
        return map;
    }

    @RequestMapping(value = "/levelTest",method = RequestMethod.GET)
    public Map<String,Object> levelTest(@RequestParam("userName")String userName,@RequestParam("password")String password){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        spiderUtil.login(userName,password,WebConstant.GET_PASSWORD);
        List<LevelTest> ltList = spiderUtil.getLevelTest();
        Map<String, Object> map = new HashMap<>();
        map.put("levelTests",ltList);
        return map;
    }



    @RequestMapping(value = "/initIndex",method = RequestMethod.GET)
    public Map<String,Object> initIndex(@RequestParam("userName")String userName,@RequestParam("password")String password,@RequestParam("bh")String bh,@RequestParam("xq")String xq){
        SpiderUtil spiderUtil = new SpiderUtil();
        spiderUtil.init();
        boolean flag = (Boolean)spiderUtil.login(userName,password,WebConstant.GET_PASSWORD).get("flag");
        Map<String, Object> map = new HashMap<>();
        if(flag){
            List<Notice> notices = spiderUtil.getNotices();
            List<Course> courses = spiderUtil.getCourseTable(bh,xq);
            Integer weekDay = DateUtil.getWeekOfDate();
            List<Course> todayCourses = new LinkedList<>();
            for (Course c: courses) {
                if(weekDay.equals(c.getXqj())){
                    todayCourses.add(c);
                }
            }
            map.put("result",WebConstant.OK);
            map.put("notices",notices);
            map.put("todayCourses",todayCourses);
        }else{
            map.put("result",WebConstant.NO);
        }

        return map;
    }

}
