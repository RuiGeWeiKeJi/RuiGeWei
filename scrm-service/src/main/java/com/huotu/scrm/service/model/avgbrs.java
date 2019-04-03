package com.huotu.scrm.service.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class avgbrs implements Serializable {

    public avgbrs() {

    }

    /**
     * 业务员
     */
    private String cus;

    /**
     * 前两个月平均数
     */
    private Integer coun;

}
