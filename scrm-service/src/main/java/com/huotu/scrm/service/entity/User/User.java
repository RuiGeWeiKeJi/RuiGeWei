package com.huotu.scrm.service.entity.User;

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
@Table(name = "rgwuse")
@Description("人员")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 人员编号
     */
    @Column(name = "USE001",nullable = false,length = 20)
    private String userId;

    /**
     * 人员姓名
     */
    @Column(name = "USE002",nullable = false,length = 50)
    private String USE002;

    /**
     * 联系电话
     */
    @Column(name = "USE003",nullable = false,length = 20)
    private String USE003;

    /**
     * 职位
     */
    @Column(name = "USE004",nullable = false,length = 20)
    private String USE004;

    /**
     * 性别
     */
    @Column(name = "USE005",nullable = false,length = 10)
    private String USE005;

    /**
     * 密码
     */
    @Column(name = "USE006")
    private String USE006;

}
