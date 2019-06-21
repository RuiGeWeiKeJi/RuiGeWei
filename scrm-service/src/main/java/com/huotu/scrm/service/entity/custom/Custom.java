package com.huotu.scrm.service.entity.custom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "rgwcus")
@Description("客户")
//此注解是类注解，作用是json序列化时将Java bean中的一些属性忽略掉，序列化和反序列化都受影响。
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIgnore  此注解用于属性或者方法上（最好是属性上），作用和上面的@JsonIgnoreProperties一样。 生成json 时不生成age 属性
public class Custom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 客户编号:程序控制生成
     */
    @Column(name = "CUS001", nullable = false, length = 20)
    private String CUS001;

    /**
     * 客户名称
     */
    @Column(name = "CUS002", nullable = false, length = 100)
    private String CUS002;

    /**
     * 联系人
     */
    @Column(name = "CUS003", length = 20)
    private String CUS003;

    /**
     * 级别
     */
    @Column(name = "CUS004", nullable = false, length = 20)
    private String CUS004;

    /**
     * 地址
     */
    @Column(name = "CUS005", length = 225)
    private String CUS005;

    /**
     * 产值最小
     */
    @Column(name = "CUS006", length = 18)
    private Double CUS006;

    /**
     * 最大产值
     */
    @Column(name = "CUS007", length = 18)
    private Double CUS007;

    /**
     * 注册资金
     */
    @Column(name = "CUS008", length = 18)
    private Double CUS008;

    /**
     * 最近联系日期
     */
    @Column(name = "CUS009", length = 6)
    private String CUS009;

    /**
     * 预计下次联系时间
     */
    @Column(name = "CUS011", length = 6)
    private String CUS011;

    /**
     * 行业
     */
    @Column(name = "CUS010", length = 50)
    private String industry;

    /**
     * 业务员
     */
    @Column(name = "CUS012", nullable = false, length = 20)
    private String salesman;

    /**
     * 来源
     */
    @Column(name = "CUS013", nullable = false, length = 20)
    private String CUS013;


    /**
     * 人数
     */
    @Column(name = "CUS015", length = 10)
    private Integer CUS015;

    /**
     * 法人
     */
    @Column(name = "CUS016", length = 20)
    private String CUS016;

    /**
     * 购买模块
     */
    @Column(name = "CUS017", length = 225)
    private String CUS017;

    /**
     * 备注
     */
    @Column(name = "CUS014", length = 225)
    private String CUS014;

    /**
     * 录入时间
     */
    @Column(name = "CUS018")
    private String CUS018;

    /**
     * 录入人
     */
    @Column(name = "CUS019")
    private String CUS019;
//
//    @OneToMany(mappedBy="rgwbrs",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@JoinColumn(name = "BRS002")
    //@JoinTable(name = "rgwbrs",joinColumns = {@JoinColumn(name = "BRS002")},inverseJoinColumns = {@JoinColumn(name = "CUS001")})
//    private List<CustomBRS> custombrs=new ArrayList<>();

}
