package com.huotu.scrm.service.repository.Maintain;

import com.huotu.scrm.service.entity.Maintain.MaintainOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintainOrderRepository extends JpaRepository<MaintainOrderEntity,Long>  , JpaSpecificationExecutor<MaintainOrderEntity> {

    /**
     * 获取单号
     * @return
     */
    @Query(value = "SELECT MAX(DER001) DER001 FROM rgwder",nativeQuery = true)
    String getByDER001Max();

}
