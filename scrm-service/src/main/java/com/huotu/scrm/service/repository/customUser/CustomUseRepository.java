package com.huotu.scrm.service.repository.customUser;


import com.huotu.scrm.service.entity.customUse.CustomUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomUseRepository extends JpaRepository<CustomUse, Long>, JpaSpecificationExecutor<CustomUse> {

    /**
     * 根据客户编号获取所有联系人
     *
     * @return
     */

    List<CustomUse> findByCustomId(String customId);

    /**
     * 获取联系人编号
     * @return
     */
    @Query(value = "select MAX(CONVERT(CUR001,SIGNED)) CUR001 from rgwcur",nativeQuery = true)
    String findOneByCode();

    /**
     * 根据客户编号和联系人编号查询数据
     * @return
     */
    //CustomUse findOneByCustomIdAndUserId(String customId, String userId);


    

}
