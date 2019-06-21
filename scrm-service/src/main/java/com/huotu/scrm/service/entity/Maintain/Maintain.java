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
@Table(name = "rgwmai")
@Description("客户运维")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Maintain  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 单号
     */
    @Column(name = "MAI001")
    private String MAI001;

    /**
     * 客户名称
     */
    @Column(name = "MAI002")
    private String MAI002;

    /**
     * 提出问题人姓名
     */
    @Column(name="MAI003")
    private String MAI003;

    /**
     * 联系方式
     */
    @Column(name = "MAI004")
    private String MAI004;

    /**
     * 提出问题方式
     */
    @Column(name = "MAI005")
    private String MAI005;

    /**
     * 提出时间
     */
    @Column(name = "MAI006")
    private Date MAI006;

    /**
     *  问题描述
     */
    @Column(name = "MAI007")
    private String MAI007;

    /**
     * 处理人
     */
    @Column(name = "MAI008")
    private String MAI008;

    /**
     * 处理进度
     */
    @Column(name = "MAI009")
    private String MAI009;

    /**
     * 预计完成时间
     */
    @Column(name = "MAI010")
    private Date MAI010;

    /**
     * 实际完成时间
     */
    @Column(name = "MAI011")
    private Date MAI011;

    /**
     * 处理方案
     */
    @Column(name = "MAI012")
    private String MAI012;

}
