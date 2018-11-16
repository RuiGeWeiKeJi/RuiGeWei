package com.huotu.scrm.service.entity.CustomBrs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Setter
@Getter
@Table(name = "rgwbrs")
@Cacheable(value = false)
//@Description("客户联系记录单")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomBRS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 联系单单号
     */
    @Column(name = "BRS001",nullable = false,length = 20)
    private String oddNum;

    /**
     * 客户编号
     */
    @Column(name = "BRS002",nullable = false,length = 20)
    private String customId;

    /**
     * 联系日期
     */
    @Column(name = "BRS003",nullable = false,length = 6)
    private String BRS003;

    /**
     * 下次联系日期
     */
    @Column(name = "BRS004",nullable = false,length = 6)
    private String BRS004;

    /**
     * 沟通方式
     */
    @Column(name = "BRS005",nullable = false,length = 20)
    private String BRS005;

    /**
     * 沟通内容
     */
    @Column(name = "BRS006",nullable = false,length = 225)
    private String BRS006;

    /**
     * 联系人
     */
    @Column(name = "BRS007",nullable = false,length = 20)
    private String BRS007;
}
