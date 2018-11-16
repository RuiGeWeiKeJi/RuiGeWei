package com.huotu.scrm.service.entity.customSRS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Table(name = "rgwsrs")
@Cacheable(value = false)
@Description("客户维护服务单")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomSRS implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 联系单单号
     */
    @Column(name = "SRS001",nullable = false,length = 20)
    private String oddNum;

    /**
     * 客户编号
     */
    @Column(name = "SRS002",nullable = false,length = 20)
    private String customId;

    /**
     * 联系日期
     */
    @Column(name = "SRS003",nullable = false,length = 6)
    private String conDate;

    /**
     * 下次联系日期
     */
    @Column(name = "SRS004",nullable = false,length = 6)
    private String SRS004;

    /**
     * 沟通方式
     */
    @Column(name = "SRS005",nullable = false,length = 20)
    private String SRS005;

    /**
     * 沟通内容
     */
    @Column(name = "SRS006",nullable = false,length = 225)
    private String SRS006;

    /**
     * 联系人
     */
    @Column(name = "SRS007",nullable = false,length = 20)
    private String SRS007;

}
