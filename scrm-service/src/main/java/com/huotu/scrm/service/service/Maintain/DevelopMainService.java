package com.huotu.scrm.service.service.Maintain;

import com.huotu.scrm.service.entity.Maintain.DevelopMain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DevelopMainService extends MainService {

    /**
     * 根据条件获取问题处理数据
     * @param specification
     * @param pageable
     * @return
     */
    Page<DevelopMain> getAllBy(Specification specification, Pageable pageable);

    /**
     * 获取最大值
     * @return
     */
    String getMaxDEV001();

    /**
     * 依据客户名称获取联系人姓名
     * @param data
     * @return
     */
    List<String> getUserInfo(String data);

    /**
     * 获取所有的技术
     * @return
     */
    List<String> getMainList();

    /**
     * 保存数据
     * @param
     */
    void saveAndRefresh(DevelopMain developMain);



}
