package com.huotu.scrm.service.entity.custom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.platform.database.events.DatabaseEventListener;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "rgwcon")
@Description("客户合同签订")
//此注解是类注解，作用是json序列化时将Java bean中的一些属性忽略掉，序列化和反序列化都受影响。
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonIgnore  此注解用于属性或者方法上（最好是属性上），作用和上面的@JsonIgnoreProperties一样。 生成json 时不生成age 属性
public class CustomCon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键由数据库自动生成（主要是自动增长型）
    @Column(name = "id")
    private Integer id;

    /**
     * 客户编号
     */
    @Column(name = "CON001")
    private String CON001;

    /**
     * 客户名称
     */
    @Column(name = "CON002")
    private String CON002;

    /**
     * 合同签订日期
     */
    @Column(name = "CON003")
    private Date CON003;

    /**
     * 合同到期日期
     */
    @Column(name = "CON004")
    private Date CON004;

    /**
     * 合同现有模块
     */
    @Column(name = "CON005")
    private String CON005;

}
