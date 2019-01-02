package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Course {
    private Long cid;
    /**
     * 课程名称
     */
    private String kcmc;
    /**
     * 星期几
     */
    private Integer xqj;
    /**
     * 上课节次
     */
    private Integer skjc;
    /**
     * 上课长度
     */
    private Integer skcd;
}
