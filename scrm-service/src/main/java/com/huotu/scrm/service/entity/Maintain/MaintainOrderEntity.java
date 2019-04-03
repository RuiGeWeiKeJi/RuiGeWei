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
@Table(name = "rgwder")
@Description("客户运维记录单")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaintainOrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 单号
     */
    @Column(name = "DER001")
    private String DER001;


    /**
     * 企明星
     */
    @Column(name = "DER002")
    private Boolean DER002;

    /**
     * 易助
     */
    @Column(name = "DER003")
    private Boolean DER003;

    /**
     * 易非
     */
    @Column(name = "DER004")
    private Boolean DER004;

    /**
     * 智物流
     */
    @Column(name = "DER005")
    private Boolean DER005;

    /**
     * 易助手
     */
    @Column(name = "DER006")
    private Boolean DER006;

    /**
     * 全新安装
     */
    @Column(name = "DER007")
    private Boolean DER007;

    /**
     * 重新安装
     */
    @Column(name = "DER008")
    private Boolean DER008;

    /**
     * 系统维护
     */
    @Column(name = "DER009")
    private Boolean DER009;

    /**
     * 顾问辅导
     */
    @Column(name = "DER010")
    private Boolean DER010;

    /**
     * 其他
     */
    @Column(name = "DER011")
    private Boolean DER011;

    /**
     * 客户名称
     */
    @Column(name = "DER012")
    private String DER012;

    /**
     * 参与人员
     */
    @Column(name = "DER013")
    private String DER013;

    /**
     * 主题
     */
    @Column(name = "DER014")
    private String DER014;

    /**
     * 填单日期
     */
    @Column(name = "DER015")
    private Date DER015;

    /**
     * 备注内容
     */
    @Column(name = "DER016")
    private String DER016;

    /**
     * 起始日期
     */
    @Column(name = "DER017")
    private Date DER017;

    /**
     * 完成日期
     */
    @Column(name = "DER018")
    private Date DER018;

    /**
     * 总工时(天)
     */
    @Column(name = "DER019")
    private String DER019;

    /**
     * 计费天数
     */
    @Column(name = "DER020")
    private Double DER020;

    /**
     * 客户签字
     */
    @Column(name = "DER021")
    private boolean DER021;

    /**
     * 附件
     */
    @Column(name = "DER022")
    private byte[] DER022;

}
