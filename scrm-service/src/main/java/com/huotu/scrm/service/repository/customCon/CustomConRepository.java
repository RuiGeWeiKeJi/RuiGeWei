package com.huotu.scrm.service.repository.customCon;


import com.huotu.scrm.service.entity.custom.CustomCon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomConRepository  extends JpaRepository<CustomCon, Long>, JpaSpecificationExecutor<CustomCon> {

    /**
     * 根据供应商名称，获取历史签合同明细
     * @param con001
     * @return
     */
    @Query(value = "SELECT id,a.CON003,CON004,CON005 FROM rgwcon a inner join (select max(CON003) CON003 FROM rgwcon WHERE CON002=?1) b on a.CON003=b.CON003 WHERE CON002=?1  ",nativeQuery = true)
    List<CustomCon> findAllByCON001(String con001);

}
