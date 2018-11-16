package com.huotu.scrm.service.service.customBrs;


import com.huotu.scrm.service.entity.CustomBrs.CustomBRS;
import com.huotu.scrm.service.entity.custom.Custom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface CustomBrsService {

    /**
     * 根据客户编号获取记录单单号
     * @return
     */
    String getId( );

    /**
     * 获取当前日期
     * @return
     */
    Date getDateNow();

    /**
     * 依据客户编号查询该客户所有联系人
     * @param customId
     * @return
     */
    List<String> findAllByCustomId(String customId);

    /**
     * 保存数据
     * @param customBRS
     */
    void insertCustomUse(CustomBRS customBRS);

    /**
     * 修改客户最近联系日期和下次联系日期
     * @param custom
     */
    void updateCustom(Custom custom);

    /**
     * 根据客户编号,查询所有联系记录单
     * @param pageable
     * @return
     */
    Page<CustomBRS> findByCustomId(Specification specification, Pageable pageable);

    /**
     * 读取客户资料联系单数据
     * @param specification
     * @return
     */
    Page<CustomBRS> readCusInfoByCon(Specification specification,Pageable pageable);

    /**
     * 删除一条记录根据单号
     * @param oddNum
     */
    void  deleteOneByOddNum(String oddNum);

}
