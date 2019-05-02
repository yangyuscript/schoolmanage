package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommonCourse {
    /**
     * 选课信息id
     */
    private Long ccid;
    /**
     * 学期
     */
    private String xq;
    /**
     * 课程代码
     */
    private String kcdm;
    /**
     * 课程名称
     */
    private String kcmc;
    /**
     * 课程类别
     */
    private String kclb;
    /**
     * 学分
     */
    private String xf;
    /**
     * 上课教师
     */
    private String skjs;
    /**
     * 上课时间
     */
    private String sksj;
}
