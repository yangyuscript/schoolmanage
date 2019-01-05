package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Score {
    private Integer sid;
    /**
     * 学号
     */
    private String xh;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 学期
     */
    private String xq;
    /**
     * 课程名称
     */
    private String kcmc;
    /**
     * 课程类别
     */
    private String lb;
    /**
     * 学分
     */
    private String xf;
    /**
     * 成绩
     */
    private String cj;
    /**
     * 考试性质
     */
    private String ksxz;
    /**
     * 基点
     */
    private String jd;
    /**
     * 课程代码
     */
    private String kcdm;
    /**
     * 学时
     */
    private String xs;
    /**
     * 必修课平均学分积点
     */
    private String pjjd;
}
