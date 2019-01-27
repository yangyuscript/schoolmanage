package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LevelTest {
    private Integer ltid;
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
     * 考试年月
     */
    private String ksny;
    /**
     * 语种代码
     */
    private String yzdm;
    /**
     * 语种名称
     */
    private String yzmc;
    /**
     * 准考证号码
     */
    private String zkzhm;
    /**
     * 考试成绩
     */
    private String kscj;
}
