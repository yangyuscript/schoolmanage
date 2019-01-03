package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Exam {
    private Long eid;
    private String school;
    private String courseCode;
    private String courseName;
    private String className;
    private String examTime;
    private String examAddress;
    private String monitorOne;
    private String monitorTwo;
    private String remark;
    private String examWeek;
    private String week;
    private String sectionOne;
    private String sectionTwo;
}
