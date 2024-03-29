package com.huotu.scrm.service.entity.baseset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "rgwflt")
@Cacheable(value = false)
@Description("达标条件")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reachflt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 类别:  级别   通话内容  通话内容数量
     */
    @Column(name = "FLT001")
    private String FLT001;

    /**
     *关系:  =  <=   >=  !=  like
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
