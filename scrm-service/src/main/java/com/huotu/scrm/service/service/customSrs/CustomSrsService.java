package com.huotu.scrm.service.service.customSrs;

import com.huotu.scrm.service.entity.customSRS.CustomSRS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;

public interface CustomSrsService {

    /**
     * 获取最大编号
     * @return
     */
    String findByCode();

    /**
     * 获取系统日期时间
     * @return
     */
    Date getDateNow();

    /**
     * 获取联系人
     * @param customId
     * @return
     */
    List<String> findByCustomId(String customId);

    /**
     * 分页获取所有记录单数据
     * @param pageable
     * @return
     */
    Page<CustomSRS> findAllByCustomId(Specification specification, Pageable pageable);

    /**
     * 保存数据
     * @param customSRS
     * @return
     */
    CustomSRS insertCusSrs(CustomSRS customSRS);

    /**
     * 删除数据
     */
    void deleteCustomSrs(CustomSRS customSRS);

    /**
     * 查找记录
     * @return
     */
    CustomSRS findAllCustomsrs(Specification specification);

}
