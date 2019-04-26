package com.huotu.scrm.service.repository.Maintain;


import com.huotu.scrm.service.entity.Maintain.Maintain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MaintainRepository extends JpaRepository<Maintain, Long>, JpaSpecificationExecutor<Maintain> {

    /**
     * 获取最大值
     * @return
     */
    @Query(value = "select MAX(MAI001) MAI001 from rgwmai",nativeQuery = true)
    String getMaxMAI001();

    /**
     * 依据客户名称获取联系人姓名
     * @param data
     * @return
     */
    @Query(value = "select a.CUR002 from rgwcur a inner join rgwcus b on a.CUR010=b.CUS001 where CUS002=?1",nativeQuery = true)
    List<String> getUserInfo(String data);

    /**
     * 获取所有的技术
     * @return
     */
    @Query(value = "select DISTINCT ROL002 from rgwrol where ROL003='技术'",nativeQuery = true)
    List<String> getMainList();

}
