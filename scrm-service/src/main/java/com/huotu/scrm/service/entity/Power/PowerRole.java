package com.huotu.scrm.service.entity.Power;

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
@Table(name = "rgwpow")
@Description("角色权限分配")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PowerRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 角色名称
     */
    @Column(name = "POW001")
    private String POW001;

    /**
     * 页面名称
     */
    @Column(name = "POW002")
    private String POW002;

    /**
     * 运行
     */
    @Column(name = "POW003")
    private boolean POW003;

    /**
     * 查询
     */
    @Column(name = "POW004")
    private boolean POW004;

    /**
     * 新建
     */
    @Column(name = "POW005")
    private boolean POW005;

    /**
     * 编辑
     */
    @Column(name = "POW006")
    private boolean POW006;

    /**
     * 删除
     */
    @Column(name = "POW007")
    private boolean POW007;

    /**
     * 编号
     */
    @Column(name = "POW008")
    private String oddNum;

}
