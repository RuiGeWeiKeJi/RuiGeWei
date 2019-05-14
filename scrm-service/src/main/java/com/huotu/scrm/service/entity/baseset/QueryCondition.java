package com.huotu.scrm.service.entity.baseset;

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
@Table(name = "rgwflt")
@Description("基础设置")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryCondition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 条件
     */
    @Column(name = "FLT001")
    private String FLT001;

    /**
     * 操作
     */
    @Column(name = "FLT002")
    private String FLT002;

    /**
     * 值
     */
    @Column(name = "FLT003")
    private String FLT003;

    /**
     * 关系
     */
    @Column(name = "FLT004")
    private String FLT004;

}
