package com.huotu.scrm.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class increasebrs implements Serializable {

    public increasebrs() {

    }

    /**
     * 姓名
     */
    private  String username;
    /**
     * 级别
     */
    private String lev;

    /**
     * 上个月
     */
    private Integer premonth;

    /**
     * 本月增长
     */
    private Integer monthadd;

    /**
     * 上周
     */
    private  Integer preweek;

    /**
     * 本周增长
     */
    private Integer weekadd;

    /**
     * 昨天
     */
    private Integer preday;

    /**
     * 今日增长
     */
    private  Integer dayadd;

}
