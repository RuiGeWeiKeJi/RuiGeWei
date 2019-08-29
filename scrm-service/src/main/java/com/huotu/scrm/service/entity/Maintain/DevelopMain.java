package com.huotu.scrm.service.entity.Maintain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "rgwdev")
@Description("开发运维")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DevelopMain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 单号
     */
    @Column(name = "DEV001")
    private String DEV001;

    /**
     * 客户名称
     */
    @Column(name = "DEV002")
    private String DEV002;

    /**
     * 提出问题人姓名
     */
    @Column(name="DEV003")
    private String DEV003;

    /**
     * 联系方式
     */
    @Column(name = "DEV004")
    private String DEV004;

    /**
     * 提出问题方式
     */
    @Column(name = "DEV005")
    private String DEV005;

    /**
     * 提出时间
     */
    @Column(name = "DEV006")
    private Date DEV006;

    /**
     *  问题描述
     */
    @Column(name = "DEV007")
    private String DEV007;

    /**
     * 处理人
     */
    @Column(name = "DEV008")
    private String DEV008;

    /**
     * 处理进度
     */
    @Column(name = "DEV009")
    private String DEV009;

    /**
     * 预计完成时间
     */
    @Column(name = "DEV010")
    private Date DEV010;

    /**
     * 实际完成时间
     */
    @Column(name = "DEV011")
    private Date DEV011;

    /**
     * 处理方案
     */
    @Column(name = "DEV012")
    private String DEV012;

    /**
     * 性质
     */
    @Column(name = "DEV013")
    private String DEV013;

}
