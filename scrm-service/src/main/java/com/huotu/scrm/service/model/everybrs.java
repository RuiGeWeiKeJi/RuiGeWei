package com.huotu.scrm.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class everybrs implements Serializable {

    public everybrs(){

    }

    /**
     * 业务员
     */
    private String cus;

    /**
     * 每日
     */
    private Integer days;

    /**
     * 打电话数
     */
    private Integer coun;

}
