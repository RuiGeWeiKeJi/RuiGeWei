package com.huotu.scrm.service.service.baseset;

import com.huotu.scrm.service.entity.baseset.BaseSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface basedataService {

    /**
     * 获取所有数据
     * @param pageable
     * @return
     */
    Page<BaseSet> findAll(Specification<BaseSet> specification, Pageable pageable);

    /**
     * 保存数据
     * @param baseSet
     */
    void saveAndRefresh(BaseSet baseSet);

    /**
     * 删除
     * @param baseSet
     * @return
     */
    void deleteItem(BaseSet baseSet);

    /**
     * 是否存在
     * @param specification
     * @return
     */
    BaseSet findOne(Specification<BaseSet> specification);

}
