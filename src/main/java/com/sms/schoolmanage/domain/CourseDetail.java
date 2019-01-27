package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDetail {
    private Integer cdid;
    private Integer cid;
    /**
     * 班级号
     */
    private String bj;
    /**
     * 课程号
     */
    private String kch;
    /**
     * 课程名称
     */
    private String kcmc;
    /**
     * 课程类别
     */
    private String kclb;
    /**
     * 考核方式
     */
    private String kh;
    /**
     * 总学时
     */
    private String zxs;
    /**
     * 学分
     */
    private String xf;
    /**
     * 上课教师
     */
    private String skjs;
    /**
     * 实践周次
     */
    private String sjzc;
}
