package com.huotu.scrm.service.repository.custom;

import com.huotu.scrm.service.entity.custom.Custom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustemInfoRepository extends JpaRepository<Custom, Long>, JpaSpecificationExecutor<Custom> {

    /**
     * 查询超期客户
     * @return
     */
    @Query(value = "select id from rgwcus where CUS011<NOW()",nativeQuery = true)
    List<Integer> findBy();

    /**
     * 依据客户编号查询该客户所有联系人
     * @return
     */
    @Query(value = "select t.USE002 from rgwuse t where t.USE004 in ('业务','业务经理') and t.USE002!='系统管理员'",nativeQuery = true)
    List<String> findAllBy();

}
