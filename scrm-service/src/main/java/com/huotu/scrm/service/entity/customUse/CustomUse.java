package com.huotu.scrm.service.entity.customUse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "rgwcur")
//@Description("客户联系人")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomUse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 人员编号
     */
    @Column(name = "CUR001",nullable = false,length = 20)
    private String userId;

    /**
     * 人员姓名
     */
    @Column(name = "CUR002",nullable = false,length = 50)
    private String CUR002;

    /**
     * 联系人职位
     */
    @Column(name = "CUR003",length = 20)
    private String CUR003;

    /**
     * 联系人电话
     */
    @Column(name = "CUR004",length = 20)
    private String CUR004;

    /**
     * 邮箱
     */
    @Column(name = "CUR005",length = 30)
    private String CUR005;

    /**
     * 微信
     */
    @Column(name = "CUR006",length = 20)
    private String CUR006;

    /**
     * 性别
     */
    @Column(name = "CUR007",nullable = false,length = 10)
    private  String CUR007;

    /**
     * 年龄
     */
    @Column(name = "CUR008",length = 10)
    private Integer CUR008;

    /**
     * 备注
     */
    @Column(name = "CUR009",length = 225)
    private String CUR009;

    /**
     * 客户编号
     */
    @Column(name = "CUR010",nullable = false,length = 20)
    private String customId;
}
