package com.huotu.scrm.service.service.Maintain;

import com.huotu.scrm.service.entity.Maintain.Maintain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface MaintainReportService {

    /**
     * 根据条件获取问题处理数据
     * @param specification
     * @param pageable
     * @return
     */
    Page<Maintain> getAllBy(Specification specification, Pageable pageable);

    /**
     * 模糊查询客户名称
     * @param customName
     * @return
     */
    List<String> getByDev002(String customName);

    /**
     * 获取最大的周
     * @return
     */
    String getWeek();

    /**
     * 查询所有客户
     * @return
     */
    List<String> getDEV002();
}
