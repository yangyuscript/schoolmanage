package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Student {
    private Integer stid;
    /**
     * 班号
     */
    private String bh;
    /**
     * 班级
     */
    private String bj;
    /**
     * 学号
     */
    private String xh;
    /**
     * 姓名
     */
    private String xm;
    /**
     * 性别
     */
    private String xb;
    /**
     * 出生日期
     */
    private String csrq;
    /**
     * 学制
     */
    private String xz;
    /**
     * 院系名称
     */
    private String yxmc;
    /**
     * 籍贯
     */
    private String jg;
    /**
     * 专业名称
     */
    private String zymc;
    /**
     * 民族
     */
    private String mz;
    /**
     * 专业代码
     */
    private String zydm;
    /**
     * 生源类别
     */
    private String sylb;
    /**
     * 身份证号
     */
    private String sfzh;
    /**
     * 年级
     */
    private String nj;
    /**
     * 政治面貌
     */
    private String zzmm;
    /**
     * 入学日期
     */
    private String rxrq;
    /**
     * 准考证号
     */
    private String zkzh;
    /**
     * 入校前学生特长
     */
    private String zxqxstc;
    /**
     * 高考成绩
     */
    private String gkcj;
    /**
     * 高考各科成绩
     */
    private String gkgkcj;
    /**
     * 入校前奖惩情况
     */
    private String rxqjcqk;
    /**
     * 家庭联系人
     */
    private String jtlxr;
    /**
     * 家庭电话
     */
    private String jtdh;
    /**
     * 家庭地址
     */
    private String jtdz;
    /**
     * 邮编
     */
    private String yb;
    /**
     * 微信号
     */
    private String wxh;
    /**
     * 本人电话
     */
    private String brdh;
    /**
     * 火车终到站
     */
    private String hczdz;
    /**
     * 入学照片
     */
    private String rxzp;
    /**
     * 在校照片
     */
    private String zxzp;
    /**
     * 毕业照片
     */
    private String byzp;


}
