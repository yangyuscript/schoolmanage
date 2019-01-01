package com.sms.schoolmanage.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notice {
    private Integer nid;
    private String title;
    private String author;
    private String content;
    private String time;
    private String url;

}
