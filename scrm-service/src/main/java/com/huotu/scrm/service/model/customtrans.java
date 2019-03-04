package com.huotu.scrm.service.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Data

public class customtrans {

    /**
     * 客户编号:程序控制生成
     */
    private String CUS001;

    /**
     * 客户名称
     */
    private String CUS002;


    /**
     * 级别
     */
    private String CUS004;

    /**
     * 沟通方式
     */
    private String BRS005;

    /**
     * 沟通内容
     */
    private String BRS006;

    /**
     * 联系人
     */
    private String BRS007;

    public customtrans(String CUS001,String CUS002, String CUS004,String BRS005,String BRS006,String BRS007){
        this.CUS001=CUS001;
        this.CUS002=CUS002;
        this.CUS004=CUS004;
        this.BRS005=BRS005;
        this.BRS006=BRS006;
        this.BRS007=BRS007;
    }

}
